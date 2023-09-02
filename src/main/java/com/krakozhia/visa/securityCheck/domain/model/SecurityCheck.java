package com.krakozhia.visa.securityCheck.domain.model;

import com.krakozhia.visa.common.AggregateRoot;
import com.krakozhia.visa.securityCheck.application.event.SecurityCheckCompletedEvent;
import com.krakozhia.visa.visaApplication.domain.info.VisaApplicationStatus;
import com.krakozhia.visa.visaApplication.domain.model.VisaApplication;

public class SecurityCheck extends AggregateRoot<SecurityCheckId> {

    private SecurityCheckId securityCheckId;
    private VisaApplication visaApplication;

    // nia : National Intelligence Agency
    private SecurityStatus niaSecurityCheckStatus;
    private SecurityStatus homelandSecurityCheckStatus;
    private SecurityStatus interpolSecurityCheckStatus;


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

    public void applyNiaSecurityCheck(SecurityStatus status) {
      this.niaSecurityCheckStatus = status;
    }

    public void applyHomelandSecurityCheck(SecurityStatus status) {
        this.homelandSecurityCheckStatus = status;
    }

    public void applyInterpolSecurityCheck(SecurityStatus status) {
        this.interpolSecurityCheckStatus = status;
    }


    public SecurityStatus niaSecurityCheckStatus() {
        return niaSecurityCheckStatus;
    }

    public SecurityStatus homelandSecurityCheckStatus() {
        return homelandSecurityCheckStatus;
    }

    public SecurityStatus interpolSecurityCheckStatus() {
        return interpolSecurityCheckStatus;
    }

    public VisaApplication visaApplication() {
        return visaApplication;
    }


    public SecurityStatus getOverallSecurityClearanceStatus() {
        if (this.homelandSecurityCheckStatus() == SecurityStatus.PASSED
                && this.niaSecurityCheckStatus() == SecurityStatus.PASSED
                && this.interpolSecurityCheckStatus() == SecurityStatus.PASSED) {
            return SecurityStatus.PASSED;
        } else {
            return SecurityStatus.FAILED;
        }
    }
}
