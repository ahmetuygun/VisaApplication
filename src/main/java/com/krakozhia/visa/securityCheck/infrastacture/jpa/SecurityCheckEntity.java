package com.krakozhia.visa.securityCheck.infrastacture.jpa;

import com.krakozhia.visa.securityCheck.domain.SecurityStatus;
import com.krakozhia.visa.visaApplication.domain.info.VisaType;
import com.krakozhia.visa.visaApplication.infrastacture.jpa.entity.VisaApplicationEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "SECURTY_CHECK")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityCheckEntity {
    @Id
    private Long id;
    private Long visaApplicationRefId;

    @Enumerated(EnumType.STRING)
    private SecurityStatus niaSecurityCheckStatus;

    @Enumerated(EnumType.STRING)
    private SecurityStatus homelandSecurityCheckStatus;

    @Enumerated(EnumType.STRING)
    private SecurityStatus interpolSecurityCheckStatus;

}
