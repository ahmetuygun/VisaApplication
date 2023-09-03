package com.krakozhia.visa.securityCheck.infrastacture;

import com.krakozhia.visa.common.UniqueIdGenerator;
import com.krakozhia.visa.securityCheck.domain.model.SecurityCheckId;
import com.krakozhia.visa.securityCheck.domain.repository.SecurityCheckRepository;
import com.krakozhia.visa.securityCheck.domain.model.SecurityCheck;
import com.krakozhia.visa.securityCheck.infrastacture.jms.JmsSecurityCheckProducer;
import com.krakozhia.visa.securityCheck.infrastacture.jms.entity.SecurityCheckMessageEntity;
import com.krakozhia.visa.securityCheck.infrastacture.jpa.JpaSecurityCheckRepository;
import com.krakozhia.visa.securityCheck.infrastacture.jpa.entity.SecurityCheckEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityCheckRepositoryImpl implements SecurityCheckRepository {

    private final JmsSecurityCheckProducer jmsSecurityCheckProducer;

    private final JpaSecurityCheckRepository jpaSecurityCheckRepository;
    @Value("${jms.queue.securityCheck.request.source1}")
    String destinationSource1;

    @Value("${jms.queue.securityCheck.request.source2}")
    String destinationSource2;

    @Value("${jms.queue.securityCheck.request.source3}")
    String destinationSource3;

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
                .source1SecurityCheckStatus(securityCheck.source1SecurityCheckStatus())
                .source2SecurityCheckStatus(securityCheck.source2SecurityCheckStatus())
                .source3SecurityCheckStatus(securityCheck.source3SecurityCheckStatus())
                .visaApplicationRefId(securityCheck.visaApplication().getId().value())
                .build();

        jpaSecurityCheckRepository.save(securityCheckEntity);

    }

    @Override
    public void sendForSource1SecurityCheck(SecurityCheck securityCheck) {
        jmsSecurityCheckProducer.send(toJmsMessage(securityCheck), destinationSource1);
    }

    @Override
    public void sendForSource2SecurityCheck(SecurityCheck securityCheck) {
        jmsSecurityCheckProducer.send(toJmsMessage(securityCheck), destinationSource2);
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
    public void sendForSource3SecurityCheck(SecurityCheck securityCheck) {
        jmsSecurityCheckProducer.send(toJmsMessage(securityCheck), destinationSource3);
    }

    @Override
    public Optional<SecurityCheck> retrieveSecurityCheck(SecurityCheckId securityCheckId) {
        return Optional.empty();
    }
}
