package com.krakozhia.visa.visaApplication.infrastacture.jpa;

import com.krakozhia.visa.visaApplication.infrastacture.jpa.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentEntityRepository extends JpaRepository<PaymentEntity, Long> {
}

