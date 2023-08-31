package com.krakozhia.visa.visaApplication.infrastacture.jpa.entity;

import com.krakozhia.visa.visaApplication.domain.info.VisaApplicationStatus;
import com.krakozhia.visa.visaApplication.domain.info.VisaType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "VISA_APPLICATION")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisaApplicationEntity {
    @Id
    private Long id;
    @ManyToOne
    private ApplicantEntity applicant;
    @OneToOne
    private PassportEntity applicantPassportInfo;
    private LocalDate applicationDate;

    @Enumerated(EnumType.STRING)
    private VisaType purposeOfVisit;
    private LocalDate intendedArriveDate;
    @ManyToOne
    private AddressEntity applicantAddress;
    @Enumerated(EnumType.STRING)
    private VisaApplicationStatus status;

    @OneToOne
    private PaymentEntity payment;

}
