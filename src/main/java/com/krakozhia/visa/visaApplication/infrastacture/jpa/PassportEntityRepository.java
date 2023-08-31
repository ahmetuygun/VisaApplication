package com.krakozhia.visa.visaApplication.infrastacture.jpa;

import com.krakozhia.visa.visaApplication.infrastacture.jpa.entity.AddressEntity;
import com.krakozhia.visa.visaApplication.infrastacture.jpa.entity.PassportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportEntityRepository extends JpaRepository<PassportEntity, Long> {
}

