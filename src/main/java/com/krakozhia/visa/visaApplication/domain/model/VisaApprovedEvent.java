package com.krakozhia.visa.visaApplication.domain.model;

import org.springframework.context.ApplicationEvent;

public class VisaApprovedEvent extends ApplicationEvent {
    private VisaApplicationId id;
    public VisaApprovedEvent(Object source,VisaApplicationId id) {
        super(source);
        this.id = id;
    }
}
