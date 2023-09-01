package com.krakozhia.visa.securityCheck.infrastacture.jms.entity;

import com.krakozhia.visa.securityCheck.domain.model.SecurityStatus;
import lombok.Data;

@Data
public class SecurityCheckResult {

    private Long securityCheckId;
    private SecurityStatus securityStatus;
}
