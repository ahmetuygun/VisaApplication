package com.krakozhia.visa.visaApplication.domain.model;

import com.krakozhia.visa.common.AggregateRoot;
import com.krakozhia.visa.common.exception.DomainException;
import com.krakozhia.visa.securityCheck.domain.SecurityStatus;
import com.krakozhia.visa.visaApplication.application.event.SecurityCheckEvent;
import com.krakozhia.visa.visaApplication.domain.info.*;

import java.time.LocalDate;

public class VisaApplication extends AggregateRoot<VisaApplicationId> {
    private Applicant applicantPersonalInfo;
    private PassportInformation applicantPassportInfo;
    private LocalDate applicationDate;
    private VisaType purposeOfVisit;
    private LocalDate intendedArriveDate;
    private Address applicantAddress;
    private VisaApplicationStatus status;
    private VisaFeeReceipt visaFeeReceipt;

    public VisaApplication(VisaApplicationId visaApplicationId,
                           Applicant applicantPersonalInfo, PassportInformation applicantPassportInfo,
                           LocalDate applicationDate, VisaType purposeOfVisit, LocalDate intendedArriveDate,
                           Address applicantAddress, VisaFeeReceipt visaFeeReceipt) {
        super(visaApplicationId);
        this.applicantPersonalInfo = applicantPersonalInfo;
        this.applicantPassportInfo = applicantPassportInfo;
        this.applicationDate = applicationDate;
        this.purposeOfVisit = purposeOfVisit;
        this.intendedArriveDate = intendedArriveDate;
        this.applicantAddress = applicantAddress;
        this.visaFeeReceipt = visaFeeReceipt;
        this.status = VisaApplicationStatus.SUBMITTED;
    }


    public void validateForSubmission() throws DomainException {


        LocalDate maxIntendedArriveDate = applicationDate.plusMonths(6);
        // Example: Allow up to 6 months from application
        if (intendedArriveDate != null && intendedArriveDate.isAfter(maxIntendedArriveDate)) {
            throw new DomainException("Intended arrival date should be within a reasonable range from the application date.");
        }

        if (visaFeeReceipt == null || !visaFeeReceipt.valid()) {
            throw new DomainException("A valid visa fee receipt is required.");
        }

        if (intendedArriveDate != null && intendedArriveDate.isBefore(applicationDate)) {
            throw new DomainException("Intended arrival date should be after the application date.");
        }
        if (applicantPersonalInfo == null) {
            throw new DomainException("Applicant personal information is required.");
        }
        if (applicantPassportInfo == null) {
            throw new DomainException("Applicant passport information is required.");
        }
        if (purposeOfVisit == null) {
            throw new DomainException("Purpose of visit must be specified.");
        }
        if (applicantAddress == null) {
            throw new DomainException("Applicant address is required.");
        }

        this.registerEvent(new SecurityCheckEvent(this,this));

    }

    public void applySecurityCheck(SecurityStatus securityStatus) {

        if (status != VisaApplicationStatus.SUBMITTED) {
            throw new IllegalStateException("Security check can only be applied to a submitted application.");
        }

        if (securityStatus == null) {
            throw new IllegalArgumentException("Security status cannot be null.");
        }

        if (status == VisaApplicationStatus.REJECTED) {
            throw new IllegalStateException("Security check has already been applied to this application.");
        }

        switch (securityStatus) {
            case PASSED -> {
                this.status = VisaApplicationStatus.APPROVED;
                this.registerEvent(new VisaApprovedEvent(this, this.getId()));
                this.registerEvent(new NotifyApplicantEvent(this, this.applicantAddress, this.applicantPersonalInfo, this.applicantPassportInfo,VisaApplicationStatus.APPROVED));
            }
            case FAILED -> {
                this.status = VisaApplicationStatus.REJECTED;
                this.registerEvent(new VisaRejectedEvent(this, this.getId()));
                this.registerEvent(new NotifyApplicantEvent(this, this.applicantAddress, this.applicantPersonalInfo, this.applicantPassportInfo,VisaApplicationStatus.REJECTED));

            }
        }

    }

    public VisaApplicationStatus status() {
        return status;
    }

    public Applicant applicantPersonalInfo() {
        return applicantPersonalInfo;
    }

    public PassportInformation applicantPassportInfo() {
        return applicantPassportInfo;
    }

    public LocalDate applicationDate() {
        return applicationDate;
    }

    public VisaType purposeOfVisit() {
        return purposeOfVisit;
    }

    public LocalDate intendedArriveDate() {
        return intendedArriveDate;
    }

    public Address applicantAddress() {
        return applicantAddress;
    }

    public VisaFeeReceipt visaFeeReceipt() {
        return visaFeeReceipt;
    }
}