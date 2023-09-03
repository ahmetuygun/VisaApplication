package com.krakozhia.visa.visaApplication.domain.repository;

import com.krakozhia.visa.common.exception.DomainException;
import com.krakozhia.visa.visaApplication.domain.model.PassportInformation;
import com.krakozhia.visa.visaApplication.domain.model.*;

import java.util.Optional;

public interface VisaApplicationRepository {
    Long generateId();
    VisaApplication retrieveVisaApplicationById(VisaApplicationId visaApplicationId) throws DomainException;

    void save(VisaApplication visaApplication);

    Optional<VisaFeeReceipt> getVisaFeeReceipt(Long paymentId);
    Optional<Applicant> getApplicant(Long applicantId);

    Optional<PassportInformation> getPassportInfo(Long applicantId);

    boolean isCountryEligibleForVisa(String countryCode);

    Optional<Address> getAddress(Long applicantId);
}
