package com.krakozhia.visa.visaApplication.infrastacture.rest.command;

import com.krakozhia.visa.visaApplication.domain.info.VisaType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VisaApplicationRequest {

    private Long applicantId;
    private String passportNumber;
    private LocalDate passportExpirationDate;
    private String passportIssuingCountryCode;
    private VisaType visaType;
    private LocalDate intendedArriveDate;
    private Long paymentId;


}
