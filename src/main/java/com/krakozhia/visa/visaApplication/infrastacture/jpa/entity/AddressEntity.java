package com.krakozhia.visa.visaApplication.infrastacture.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ADDRESS")
public class AddressEntity {
    @Id
    private Long id;
    private String country;
    private String city;
    private String postCode;
    private String address;
    private String phoneNumber;
}
