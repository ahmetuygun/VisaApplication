package com.krakozhia.visa.visaApplication.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

public class Passport {

    private String passportNumber;
    private LocalDate expirationDate;
    private String issuingCountry;
}
