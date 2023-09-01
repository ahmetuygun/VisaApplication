package com.krakozhia.visa.visaApplication.application.event;

import com.krakozhia.visa.visaApplication.domain.model.PassportInformation;
import com.krakozhia.visa.visaApplication.domain.info.VisaApplicationStatus;
import com.krakozhia.visa.visaApplication.domain.model.Address;
import com.krakozhia.visa.visaApplication.domain.model.Applicant;
import org.springframework.context.ApplicationEvent;

public class NotifyApplicantEvent extends ApplicationEvent {

    private Address applicantAddress;
    private Applicant applicantPersonalInfo;
    private PassportInformation applicantPassportInfo;
    private VisaApplicationStatus status;

    public NotifyApplicantEvent(Object source, Address applicantAddress, Applicant applicantPersonalInfo, PassportInformation applicantPassportInfo,VisaApplicationStatus status) {
        super(source);
        this.applicantAddress = applicantAddress;
        this.applicantPersonalInfo = applicantPersonalInfo;
        this.applicantPassportInfo = applicantPassportInfo;
        this.status = status;
    }
}
