package com.krakozhia.visa.securityCheck.infrastacture.jpa.entity;

import com.krakozhia.visa.securityCheck.domain.model.SecurityStatus;
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
