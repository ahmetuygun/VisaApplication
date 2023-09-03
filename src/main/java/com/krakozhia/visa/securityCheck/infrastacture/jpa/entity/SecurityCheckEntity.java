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
    private SecurityStatus source1SecurityCheckStatus;

    @Enumerated(EnumType.STRING)
    private SecurityStatus source2SecurityCheckStatus;

    @Enumerated(EnumType.STRING)
    private SecurityStatus source3SecurityCheckStatus;

}
