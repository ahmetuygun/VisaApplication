package com.krakozhia.visa.securityCheck.application.event;

import com.krakozhia.visa.securityCheck.application.SecurityCheckService;
import com.krakozhia.visa.visaApplication.application.event.SecurityCheckEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SecurityCheckEventListener {
    private final SecurityCheckService securityCheckService;

    public SecurityCheckEventListener(SecurityCheckService securityCheckService) {
        this.securityCheckService = securityCheckService;
    }

    @EventListener
    public void handle(SecurityCheckEvent securityCheckEvent) {

        securityCheckService.processSecurityCheckRequest(securityCheckEvent);

    }

}
