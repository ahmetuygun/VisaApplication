package com.krakozhia.visa.visaApplication.infrastacture.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "APPLICANT")
public class ApplicantEntity {
    @Id
    private Long id;
    private String fullName;
    private Date birthDate;
    private String nationality;



}
