package com.krakozhia.visa.visaApplication.domain.info;

import com.krakozhia.visa.visaApplication.domain.model.PassportInfoId;

import java.time.LocalDate;

public class PassportInformation {
    private PassportInfoId passportInfoId;
    private String passportNumber;
    private LocalDate expirationDate;
    private FictionalCountry country;


    public PassportInformation(String passportNumber, LocalDate expirationDate, String issuingCountryCode) {
        this.passportNumber = passportNumber;
        this.expirationDate = expirationDate;
        this.country = FictionalCountry.getCountryByCode(issuingCountryCode);
    }
    public PassportInformation(PassportInfoId passportInfoId, String passportNumber, LocalDate expirationDate, FictionalCountry country) {
        this.passportInfoId = passportInfoId;
        this.passportNumber = passportNumber;
        this.expirationDate = expirationDate;
        this.country = country;
    }

    public String passportNumber() {
        return passportNumber;
    }

    public LocalDate expirationDate() {
        return expirationDate;
    }

    public FictionalCountry country() {
        return country;
    }
}
