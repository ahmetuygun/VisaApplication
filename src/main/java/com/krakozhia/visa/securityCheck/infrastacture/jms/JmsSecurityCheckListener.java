package com.krakozhia.visa.securityCheck.infrastacture.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krakozhia.visa.common.exception.DomainException;
import com.krakozhia.visa.securityCheck.application.SecurityCheckService;
import com.krakozhia.visa.securityCheck.domain.model.SecurityCheckSource;
import com.krakozhia.visa.securityCheck.infrastacture.jms.entity.SecurityCheckResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JmsSecurityCheckListener {

    private final SecurityCheckService securityCheckService;

    public JmsSecurityCheckListener(SecurityCheckService securityCheckService) {
        this.securityCheckService = securityCheckService;
    }


    @JmsListener(destination = "${jms.queue.securityCheck.response.source3}")
    public void receiveSource3SecurityCheckResponse(String message) {
        SecurityCheckResult checkResult;

        try {
            checkResult = new ObjectMapper().readValue(message, SecurityCheckResult.class);
            securityCheckService.processSecurityCheckResponse(checkResult, SecurityCheckSource.SOURCE3);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (DomainException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    @JmsListener(destination = "${jms.queue.securityCheck.response.source1}")
    public void receiveSource1SecurityCheckResponse(String message) {
        SecurityCheckResult checkResult;

        try {
            checkResult = new ObjectMapper().readValue(message, SecurityCheckResult.class);
            securityCheckService.processSecurityCheckResponse(checkResult, SecurityCheckSource.SOURCE1);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (DomainException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    @JmsListener(destination = "${jms.queue.securityCheck.response.source2}")
    public void receiveSource2SecurityCheckResponse(String message) {
        SecurityCheckResult checkResult;

        try {
            checkResult = new ObjectMapper().readValue(message, SecurityCheckResult.class);
            securityCheckService.processSecurityCheckResponse(checkResult, SecurityCheckSource.SOURCE2);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (DomainException e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
