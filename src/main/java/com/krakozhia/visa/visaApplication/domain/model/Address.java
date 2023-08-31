package com.krakozhia.visa.visaApplication.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


public class Address {

    private AddressId addressId;
    private String country;
    private String city;
    private String postCode;
    private String address;
    private String phoneNumber;


    public Address(AddressId addressId, String country, String city, String postCode, String address, String phoneNumber) {
        this.addressId = addressId;
        this.country = country;
        this.city = city;
        this.postCode = postCode;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public AddressId getId() {
        return addressId;
    }

    public AddressId addressId() {
        return addressId;
    }

    public String country() {
        return country;
    }

    public String city() {
        return city;
    }

    public String postCode() {
        return postCode;
    }

    public String address() {
        return address;
    }

    public String phoneNumber() {
        return phoneNumber;
    }
}
