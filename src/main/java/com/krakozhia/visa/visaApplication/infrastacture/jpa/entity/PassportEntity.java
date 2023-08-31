package com.krakozhia.visa.visaApplication.infrastacture.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "PASSPORT")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassportEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String passportNumber;
    private LocalDate expirationDate;
    private String issuingCountry;

}
