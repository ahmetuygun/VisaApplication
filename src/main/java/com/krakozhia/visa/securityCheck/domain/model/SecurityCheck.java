package com.krakozhia.visa.securityCheck.domain.model;

import com.krakozhia.visa.common.AggregateRoot;
import com.krakozhia.visa.securityCheck.application.event.SecurityCheckCompletedEvent;
import com.krakozhia.visa.visaApplication.domain.info.VisaApplicationStatus;
import com.krakozhia.visa.visaApplication.domain.model.VisaApplication;

public class SecurityCheck extends AggregateRoot<SecurityCheckId> {

    private SecurityCheckId securityCheckId;
    private VisaApplication visaApplication;

    private SecurityStatus source1SecurityCheckStatus;
    private SecurityStatus source2SecurityCheckStatus;
    private SecurityStatus source3SecurityCheckStatus;


    public SecurityCheck(SecurityCheckId securityCheckId,
                         VisaApplication visaApplication) {
        super(securityCheckId);
        this.visaApplication = visaApplication;
    }

    public void validateForSubmission() {
        // Ensure that the security check has a valid applicant and visa application
        if (visaApplication == null ) {
            throw new IllegalStateException("Security check must have a valid applicant ");
        }

        // Check the status of the security check before submission
        if (visaApplication.status() != VisaApplicationStatus.SUBMITTED) {
            throw new IllegalStateException("Security check must be in submitted status for submission.");
        }
    }

    public void finaliseSecurityCheck() {
        SecurityCheckCompletedEvent checkResponseEvent = new SecurityCheckCompletedEvent(this, this.visaApplication().getId());
        checkResponseEvent.setSecurityStatus(getOverallSecurityClearanceStatus());
        registerEvent(checkResponseEvent);
    }

    public void applySource1SecurityCheck(SecurityStatus status) {
      this.source1SecurityCheckStatus = status;
    }

    public void applySource2SecurityCheck(SecurityStatus status) {
        this.source2SecurityCheckStatus = status;
    }

    public void applySource3SecurityCheck(SecurityStatus status) {
        this.source3SecurityCheckStatus = status;
    }


    public SecurityStatus source1SecurityCheckStatus() {
        return source1SecurityCheckStatus;
    }

    public SecurityStatus source2SecurityCheckStatus() {
        return source2SecurityCheckStatus;
    }

    public SecurityStatus source3SecurityCheckStatus() {
        return source3SecurityCheckStatus;
    }

    public VisaApplication visaApplication() {
        return visaApplication;
    }


    public SecurityStatus getOverallSecurityClearanceStatus() {
        if (this.source1SecurityCheckStatus() == SecurityStatus.PASSED
                && this.source2SecurityCheckStatus() == SecurityStatus.PASSED
                && this.source3SecurityCheckStatus() == SecurityStatus.PASSED) {
            return SecurityStatus.PASSED;
        } else {
            return SecurityStatus.FAILED;
        }
    }
}
