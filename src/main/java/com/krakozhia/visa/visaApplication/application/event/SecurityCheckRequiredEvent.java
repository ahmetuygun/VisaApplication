package com.krakozhia.visa.visaApplication.application.event;

import com.krakozhia.visa.visaApplication.domain.model.VisaApplication;
import org.springframework.context.ApplicationEvent;

public class SecurityCheckRequiredEvent extends ApplicationEvent {
    private VisaApplication visaApplication ;


    public SecurityCheckRequiredEvent(Object source, VisaApplication visaApplication) {
        super(source);
        this.visaApplication = visaApplication;

    }

    public VisaApplication getVisaApplicationId() {
        return visaApplication;
    }

}