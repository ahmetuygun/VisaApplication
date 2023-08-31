package com.krakozhia.visa.securityCheck.infrastacture.jms.entity;


import com.krakozhia.visa.visaApplication.domain.info.FictionalCountry;
import com.krakozhia.visa.visaApplication.domain.model.AddressId;
import com.krakozhia.visa.visaApplication.domain.model.PassportInfoId;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class SecurityCheckMessageEntity {

    private String passportNumber;
    private String country;
    private String city;
    private String postCode;
    private String address;
    private String phoneNumber;



}
