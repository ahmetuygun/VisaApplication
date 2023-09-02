package com.krakozhia.visa.visaApplication.infrastacture.jpa;

import com.krakozhia.visa.visaApplication.infrastacture.jpa.entity.PassportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPassportEntityRepository extends JpaRepository<PassportEntity, Long> {
}

