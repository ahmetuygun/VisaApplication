package com.krakozhia.visa.securityCheck.application;

import com.krakozhia.visa.common.exception.DomainException;
import com.krakozhia.visa.securityCheck.domain.model.SecurityCheck;
import com.krakozhia.visa.securityCheck.domain.model.SecurityCheckId;
import com.krakozhia.visa.securityCheck.domain.model.SecurityCheckSource;
import com.krakozhia.visa.securityCheck.domain.model.SecurityStatus;
import com.krakozhia.visa.securityCheck.domain.repository.SecurityCheckRepository;
import com.krakozhia.visa.securityCheck.infrastacture.SecurityCheckRepositoryImpl;
import com.krakozhia.visa.securityCheck.infrastacture.jms.entity.SecurityCheckResult;
import com.krakozhia.visa.visaApplication.application.VisaApplicationService;
import com.krakozhia.visa.visaApplication.application.event.SecurityCheckRequiredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

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

    public void processSecurityCheckRequest(SecurityCheckRequiredEvent securityCheckRequiredEvent) {

        SecurityCheck securityCheck = new SecurityCheck(new SecurityCheckId(securityCheckRepository.generateId()), securityCheckRequiredEvent.getVisaApplicationId());
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
        if (getSecurityCheckFinalised(securityCheck)) {
            securityCheck.finaliseSecurityCheck();
        }

        securityCheckRepository.save(securityCheck);

        securityCheck.getDomainEvents().stream().forEach(applicationEventPublisher::publishEvent);
        securityCheck.clearDomainEvents();


    }

    public Boolean getSecurityCheckFinalised(SecurityCheck securityCheck) {
        return securityCheck.homelandSecurityCheckStatus() != SecurityStatus.PENDING
                && securityCheck.niaSecurityCheckStatus() != SecurityStatus.PENDING
                && securityCheck.interpolSecurityCheckStatus() != SecurityStatus.PENDING;
    }


}
