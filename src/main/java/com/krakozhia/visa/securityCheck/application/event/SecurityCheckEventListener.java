package com.krakozhia.visa.securityCheck.application.event;

import com.krakozhia.visa.securityCheck.application.SecurityCheckService;
import com.krakozhia.visa.visaApplication.application.event.SecurityCheckRequiredEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SecurityCheckEventListener {
    private final SecurityCheckService securityCheckService;

    public SecurityCheckEventListener(SecurityCheckService securityCheckService) {
        this.securityCheckService = securityCheckService;
    }

    @EventListener
    public void handle(SecurityCheckRequiredEvent securityCheckRequiredEvent) {

        securityCheckService.processSecurityCheckRequest(securityCheckRequiredEvent);

    }

}
