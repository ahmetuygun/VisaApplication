package com.krakozhia.visa.securityCheck.infrastacture.jpa;

import com.krakozhia.visa.securityCheck.infrastacture.jpa.entity.SecurityCheckEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSecurityCheckRepository extends JpaRepository<SecurityCheckEntity, Long> {
}

