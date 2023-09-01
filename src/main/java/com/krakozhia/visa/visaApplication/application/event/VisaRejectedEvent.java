package com.krakozhia.visa.visaApplication.application.event;

import com.krakozhia.visa.visaApplication.domain.model.VisaApplicationId;
import org.springframework.context.ApplicationEvent;

public class VisaRejectedEvent extends ApplicationEvent {
    private VisaApplicationId id;
    public VisaRejectedEvent(Object source, VisaApplicationId id) {
        super(source);
        this.id = id;
    }
}
