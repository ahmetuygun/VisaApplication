package com.krakozhia.visa.common;

import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot<T> {

    private T id;
    private List<ApplicationEvent> domainEvents = new ArrayList<>();

    // Constructor
    protected AggregateRoot(T id) {
        this.id = id;
    }

    // Getter for the aggregate root's identifier
    public T getId() {
        return id;
    }

    // Getter for the aggregate's domain events
    public List<ApplicationEvent> getDomainEvents() {
        return domainEvents;
    }

    // Apply a domain event to the aggregate
    protected void registerEvent(ApplicationEvent event) {
        domainEvents.add(event);
    }

    // Clear domain events after they have been handled
    public void clearDomainEvents() {
        domainEvents.clear();
    }

}
