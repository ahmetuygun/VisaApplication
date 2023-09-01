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


    @JmsListener(destination = "${jms.queue.securityCheck.response.nia}")
    public void receiveNiaSecurityCheckResponse(String message) {
        SecurityCheckResult checkResult;

        try {
            checkResult = new ObjectMapper().readValue(message, SecurityCheckResult.class);
            securityCheckService.processSecurityCheckResponse(checkResult, SecurityCheckSource.NIA);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (DomainException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    @JmsListener(destination = "${jms.queue.securityCheck.response.interpol}")
    public void receiveInterpolSecurityCheckResponse(String message) {
        SecurityCheckResult checkResult;

        try {
            checkResult = new ObjectMapper().readValue(message, SecurityCheckResult.class);
            securityCheckService.processSecurityCheckResponse(checkResult, SecurityCheckSource.INTERPOL);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (DomainException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    @JmsListener(destination = "${jms.queue.securityCheck.response.homeland}")
    public void receiveHomelandSecurityCheckResponse(String message) {
        SecurityCheckResult checkResult;

        try {
            checkResult = new ObjectMapper().readValue(message, SecurityCheckResult.class);
            securityCheckService.processSecurityCheckResponse(checkResult, SecurityCheckSource.HOMELAND);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (DomainException e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
