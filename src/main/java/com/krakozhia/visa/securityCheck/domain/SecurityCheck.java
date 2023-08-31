package com.krakozhia.visa.securityCheck.domain;

import com.krakozhia.visa.common.AggregateRoot;
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

    public void applyNiaSecurityCheck(SecurityStatus status) {

    }

    public void applyHomelandSecurityCheck(SecurityStatus status) {

    }

    public void applyInterpolSecurityCheck(SecurityStatus status) {

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
}
