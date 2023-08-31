package com.krakozhia.visa.securityCheck.application;

import com.krakozhia.visa.common.exception.DomainException;
import com.krakozhia.visa.securityCheck.application.event.SecurityCheckResponseEvent;
import com.krakozhia.visa.securityCheck.domain.*;
import com.krakozhia.visa.securityCheck.infrastacture.SecurityCheckRepositoryImpl;
import com.krakozhia.visa.securityCheck.infrastacture.jms.SecurityCheckResult;
import com.krakozhia.visa.visaApplication.application.VisaApplicationService;
import com.krakozhia.visa.visaApplication.application.event.SecurityCheckEvent;
import com.krakozhia.visa.visaApplication.domain.model.VisaApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@Slf4j
public class SecurityCheckService {

    private final SecurityCheckRepository securityCheckRepository;

    private final VisaApplicationService visaApplicationService;

    private final ApplicationEventPublisher applicationEventPublisher;

    public SecurityCheckService(SecurityCheckRepositoryImpl securityCheckRepository, VisaApplicationService visaApplicationService, ApplicationEventPublisher applicationEventPublisher) {
        this.securityCheckRepository = securityCheckRepository;
        this.visaApplicationService = visaApplicationService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void processSecurityCheckRequest(SecurityCheckEvent securityCheckEvent) {

        SecurityCheck securityCheck = new SecurityCheck(new SecurityCheckId(securityCheckRepository.generateId()), securityCheckEvent.getVisaApplicationId());
        securityCheck.validateForSubmission();
        securityCheckRepository.save(securityCheck);
        securityCheckRepository.sendForNiaSecurityCheck(securityCheck);
        securityCheckRepository.sendForHomelandSecurityCheck(securityCheck);
        securityCheckRepository.sendForInterpolSecurityCheck(securityCheck);

    }

    public void processSecurityCheckResponse(SecurityCheckResult checkResult, SecurityCheckSource checkSource) throws DomainException {

        SecurityCheck securityCheck = securityCheckRepository.
                retreiveSecurityCheck(new SecurityCheckId(checkResult.getSecurityCheckId()))
                .orElseThrow(() -> new DomainException("SecurityCheck couldn't find"));

        switch (checkSource) {
            case NIA -> securityCheck.applyNiaSecurityCheck(checkResult.getSecurityStatus());
            case HOMELAND -> securityCheck.applyHomelandSecurityCheck(checkResult.getSecurityStatus());
            case INTERPOL -> securityCheck.applyInterpolSecurityCheck(checkResult.getSecurityStatus());
        }
        securityCheckRepository.save(securityCheck);

      if (getSecurityCheckFinalised(securityCheck)) {
          SecurityCheckResponseEvent checkResponseEvent = new SecurityCheckResponseEvent(this, securityCheck.visaApplication().getId());
          checkResponseEvent.setSecurityStatus(getOverallSecurityClearanceStatus(securityCheck));
          securityCheck.getDomainEvents().stream().forEach(applicationEventPublisher::publishEvent);
          securityCheck.clearDomainEvents();
        }


    }

    public Boolean getSecurityCheckFinalised(SecurityCheck securityCheck) {
        return securityCheck.homelandSecurityCheckStatus() != SecurityStatus.PENDING
                && securityCheck.niaSecurityCheckStatus() != SecurityStatus.PENDING
                && securityCheck.interpolSecurityCheckStatus() != SecurityStatus.PENDING;
    }
    public SecurityStatus getOverallSecurityClearanceStatus(SecurityCheck securityCheck) {
        if (securityCheck.homelandSecurityCheckStatus() == SecurityStatus.PASSED
                && securityCheck.niaSecurityCheckStatus() == SecurityStatus.PASSED
                && securityCheck.interpolSecurityCheckStatus() == SecurityStatus.PASSED) {
            return SecurityStatus.PASSED;
        } else {
            return SecurityStatus.FAILED;
        }
    }

}
