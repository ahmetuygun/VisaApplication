package com.krakozhia.visa.visaApplication.infrastacture.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "VISA_FEE_RECEIPT")
public class PaymentEntity {
    @Id
    private Long id;
    private BigDecimal chargedAmount;
    private Boolean valid;


}
