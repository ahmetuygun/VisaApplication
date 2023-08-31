package com.krakozhia.visa.visaApplication.infrastacture.jpa;

import com.krakozhia.visa.visaApplication.infrastacture.jpa.entity.VisaApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisaApplicationEntityRepository extends JpaRepository<VisaApplicationEntity, String> {
    
    Optional<VisaApplicationEntity> findById(Long refNumber);

}

