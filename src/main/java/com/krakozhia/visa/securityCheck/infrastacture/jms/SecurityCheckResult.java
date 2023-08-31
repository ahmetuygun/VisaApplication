package com.krakozhia.visa.securityCheck.infrastacture.jms;

import com.krakozhia.visa.securityCheck.domain.SecurityStatus;
import lombok.Data;

@Data
public class SecurityCheckResult {

    private Long securityCheckId;
    private SecurityStatus securityStatus;
}
