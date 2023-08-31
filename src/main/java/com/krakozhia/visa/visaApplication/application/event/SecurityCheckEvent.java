package com.krakozhia.visa.visaApplication.application.event;

import com.krakozhia.visa.visaApplication.domain.model.VisaApplication;
import com.krakozhia.visa.visaApplication.domain.model.VisaApplicationId;
import org.springframework.context.ApplicationEvent;

public class SecurityCheckEvent extends ApplicationEvent {
    private VisaApplication visaApplication ;


    public SecurityCheckEvent(Object source, VisaApplication visaApplication) {
        super(source);
        this.visaApplication = visaApplication;

    }

    public VisaApplication getVisaApplicationId() {
        return visaApplication;
    }

}