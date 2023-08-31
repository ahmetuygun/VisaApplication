package com.krakozhia.visa.securityCheck.infrastacture.jpa;

import com.krakozhia.visa.visaApplication.infrastacture.jpa.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSecurityCheckRepository extends JpaRepository<SecurityCheckEntity, Long> {
}

