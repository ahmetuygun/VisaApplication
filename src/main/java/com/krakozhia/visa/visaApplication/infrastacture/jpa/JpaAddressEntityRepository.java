package com.krakozhia.visa.visaApplication.infrastacture.jpa;

import com.krakozhia.visa.visaApplication.infrastacture.jpa.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAddressEntityRepository extends JpaRepository<AddressEntity, Long> {
}

