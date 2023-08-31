package com.krakozhia.visa.securityCheck.infrastacture;

import com.krakozhia.visa.common.UniqueIdGenerator;
import com.krakozhia.visa.securityCheck.domain.SecurityCheckId;
import com.krakozhia.visa.securityCheck.domain.SecurityCheckRepository;
import com.krakozhia.visa.securityCheck.domain.SecurityCheck;
import com.krakozhia.visa.securityCheck.infrastacture.jms.JmsSecurityCheckProducer;
import com.krakozhia.visa.securityCheck.infrastacture.jms.entity.SecurityCheckMessageEntity;
import com.krakozhia.visa.securityCheck.infrastacture.jpa.JpaSecurityCheckRepository;
import com.krakozhia.visa.securityCheck.infrastacture.jpa.SecurityCheckEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityCheckRepositoryImpl implements SecurityCheckRepository {

    private final JmsSecurityCheckProducer jmsSecurityCheckProducer;

    private final JpaSecurityCheckRepository jpaSecurityCheckRepository;
    @Value("${jms.queue.securityCheck.request.nia}")
    String destinationNia;

    @Value("${jms.queue.securityCheck.request.homeland}")
    String destinationHomeland;

    @Value("${jms.queue.securityCheck.request.interpol}")
    String destinationInterpol;

    public SecurityCheckRepositoryImpl(JmsSecurityCheckProducer jmsSecurityCheckProducer, JpaSecurityCheckRepository securityCheckRepository) {
        this.jmsSecurityCheckProducer = jmsSecurityCheckProducer;
        this.jpaSecurityCheckRepository = securityCheckRepository;
    }


    @Override
    public Long generateId() {
        return UniqueIdGenerator.generateUniqueId();
    }

    @Override
    public void save(SecurityCheck securityCheck) {

        SecurityCheckEntity securityCheckEntity = SecurityCheckEntity.builder()
                .id(securityCheck.getId().value())
                .niaSecurityCheckStatus(securityCheck.niaSecurityCheckStatus())
                .homelandSecurityCheckStatus(securityCheck.homelandSecurityCheckStatus())
                .interpolSecurityCheckStatus(securityCheck.interpolSecurityCheckStatus())
                .visaApplicationRefId(securityCheck.visaApplication().getId().value())
                .build();

        jpaSecurityCheckRepository.save(securityCheckEntity);

    }

    @Override
    public void sendForNiaSecurityCheck(SecurityCheck securityCheck) {
        jmsSecurityCheckProducer.send(toJmsMessage(securityCheck), destinationNia);
    }

    @Override
    public void sendForHomelandSecurityCheck(SecurityCheck securityCheck) {
        jmsSecurityCheckProducer.send(toJmsMessage(securityCheck), destinationHomeland);
    }

    private SecurityCheckMessageEntity toJmsMessage(SecurityCheck securityCheck) {

       return SecurityCheckMessageEntity.builder()
                .passportNumber(securityCheck.visaApplication().applicantPassportInfo().passportNumber())
                .phoneNumber(securityCheck.visaApplication().applicantAddress().phoneNumber())
                .city(securityCheck.visaApplication().applicantAddress().city())
                .address(securityCheck.visaApplication().applicantAddress().address())
                .postCode(securityCheck.visaApplication().applicantAddress().postCode())
                .country(securityCheck.visaApplication().applicantAddress().country())
                .build();

    }

    @Override
    public void sendForInterpolSecurityCheck(SecurityCheck securityCheck) {
        jmsSecurityCheckProducer.send(toJmsMessage(securityCheck), destinationInterpol);
    }

    @Override
    public Optional<SecurityCheck> retreiveSecurityCheck(SecurityCheckId securityCheckId) {
        return Optional.empty();
    }
}
