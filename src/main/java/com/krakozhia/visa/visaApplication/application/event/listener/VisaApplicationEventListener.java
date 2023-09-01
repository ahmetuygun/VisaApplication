package com.krakozhia.visa.visaApplication.application.event.listener;

import com.krakozhia.visa.common.exception.DomainException;
import com.krakozhia.visa.securityCheck.application.event.SecurityCheckResponseEvent;
import com.krakozhia.visa.visaApplication.application.VisaApplicationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class VisaApplicationEventListener {
    private final VisaApplicationService visaApplicationService;

    public VisaApplicationEventListener(VisaApplicationService visaApplicationService) {
        this.visaApplicationService = visaApplicationService;
    }


    @EventListener
    public void handle(SecurityCheckResponseEvent checkResponseEvent) throws DomainException {

        visaApplicationService.processSecurityCheckResponse(checkResponseEvent);

    }



}
