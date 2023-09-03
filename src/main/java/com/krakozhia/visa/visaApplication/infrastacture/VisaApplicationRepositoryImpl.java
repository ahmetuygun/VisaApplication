package com.krakozhia.visa.visaApplication.infrastacture;

import com.krakozhia.visa.common.UniqueIdGenerator;
import com.krakozhia.visa.common.exception.DomainException;
import com.krakozhia.visa.visaApplication.domain.info.FictionalCountry;
import com.krakozhia.visa.visaApplication.domain.model.PassportInformation;
import com.krakozhia.visa.visaApplication.domain.model.*;
import com.krakozhia.visa.visaApplication.domain.repository.VisaApplicationRepository;
import com.krakozhia.visa.visaApplication.infrastacture.jpa.*;
import com.krakozhia.visa.visaApplication.infrastacture.jpa.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class VisaApplicationRepositoryImpl implements VisaApplicationRepository {
    private final JpaAddressEntityRepository addressEntityRepository;
    private final JpaApplicantEntityRepository applicantEntityRepository;
    private final JpaPassportEntityRepository passportEntityRepository;
    private final JpaPaymentEntityRepository paymentEntityRepository;
    private final JpaVisaApplicationEntityRepository visaApplicationEntityRepository;
    private final JmsTemplate jmsTemplate;

    public VisaApplicationRepositoryImpl(JpaAddressEntityRepository addressEntityRepository, JpaApplicantEntityRepository applicantEntityRepository, JpaPassportEntityRepository passportEntityRepository, JpaPaymentEntityRepository paymentEntityRepository, JpaVisaApplicationEntityRepository visaApplicationEntityRepository, JmsTemplate jmsTemplate) {
        this.addressEntityRepository = addressEntityRepository;
        this.applicantEntityRepository = applicantEntityRepository;
        this.passportEntityRepository = passportEntityRepository;
        this.paymentEntityRepository = paymentEntityRepository;
        this.visaApplicationEntityRepository = visaApplicationEntityRepository;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public Long generateId() {
        return UniqueIdGenerator.generateUniqueId();
    }

    @Override
    public Optional<VisaFeeReceipt> getVisaFeeReceipt(Long paymentId) {

        return paymentEntityRepository.findById(paymentId)
                .map(entity -> new VisaFeeReceipt(new VisaFeeReceiptId(entity.getId()), entity.getChargedAmount(), entity.getValid()));

    }

    @Override
    public Optional<Applicant> getApplicant(Long applicantId) {
        return applicantEntityRepository.findById(applicantId)
                .map(entity -> new Applicant(new ApplicantId(entity.getId()), entity.getFullName(), entity.getBirthDate(), entity.getNationality()));
    }

    @Override
    public Optional<PassportInformation> getPassportInfo(Long applicantId) {
        return passportEntityRepository.findById(applicantId)
                .map(entity -> new PassportInformation(new PassportInfoId(entity.getId()), entity.getPassportNumber(),
                        entity.getExpirationDate(), FictionalCountry.getCountryByCode(entity.getIssuingCountry())));
    }

    @Override
    public boolean isCountryEligibleForVisa(String countryCode) {
        return true;// assuming have visa policy
    }

    @Override
    public Optional<Address> getAddress(Long applicantId) {
        return addressEntityRepository.findById(applicantId)
                .map(entity -> new Address(new AddressId(entity.getId()), entity.getCountry(), entity.getCity(), entity.getPostCode(), entity.getAddress(), entity.getPhoneNumber()));
    }

    @Override
    @Transactional
    public void save(VisaApplication visaApplication) {
        PassportInformation passportInformation = visaApplication.applicantPassportInfo();
        PassportEntity passportEntity = passportEntityRepository.save(toPassportEntity(passportInformation));
        visaApplicationEntityRepository.save(toVisaApplicationEntity(visaApplication, passportEntity));
    }

    private PassportEntity toPassportEntity(PassportInformation passportInformation) {
        return PassportEntity
                .builder()
                .passportNumber(passportInformation.passportNumber())
                .expirationDate(passportInformation.expirationDate())
                .issuingCountry(passportInformation.country().name())
                .build();

    }


    @Override
    public VisaApplication retrieveVisaApplicationById(VisaApplicationId visaApplicationId) throws DomainException {

        VisaApplicationEntity visaApplicationEntity =  visaApplicationEntityRepository.findById(visaApplicationId.value())
                .orElseThrow(() -> new RuntimeException("the Visa Application couldn't found"));

        Applicant applicant = getApplicant(visaApplicationEntity.getApplicant().getId())
                .orElseThrow(() -> new DomainException("Applicant couldn't found. "));

        PassportInformation passportInformation = getPassportInfo(visaApplicationEntity.getApplicantPassportInfo().getId())
                .orElseThrow(() -> new DomainException("Passport Information couldn't found. "));

        Address address = getAddress(visaApplicationEntity.getApplicantAddress().getId())
                .orElseThrow(() -> new DomainException("Address Information couldn't found. "));

        VisaFeeReceipt visaPayment = getVisaFeeReceipt(visaApplicationEntity.getPayment().getId())
                .orElseThrow(() -> new DomainException("Visa Fee Receipt Information couldn't found. "));

        VisaApplication visaApplication = new VisaApplication(new VisaApplicationId(visaApplicationEntity.getId()),
                applicant, passportInformation, LocalDate.now(), visaApplicationEntity.getPurposeOfVisit(), visaApplicationEntity.getIntendedArriveDate(), address, visaPayment);

       return visaApplication;
    }


    private VisaApplicationEntity toVisaApplicationEntity(VisaApplication visaApplication, PassportEntity passportEntity) {

        ApplicantEntity applicant = applicantEntityRepository
                .findById(visaApplication.applicantPersonalInfo().applicantId().value())
                .orElseThrow(() -> new RuntimeException("the Applicant couldn't found"));

        PaymentEntity payment = paymentEntityRepository
                .findById(visaApplication.visaFeeReceipt().id().value())
                .orElseThrow(() -> new RuntimeException("the Applicant couldn't found"));

        AddressEntity address = addressEntityRepository
                .findById(visaApplication.applicantAddress().getId().value())
                .orElseThrow(() -> new RuntimeException("the Applicant couldn't found"));

        return VisaApplicationEntity
                .builder()
                .id(visaApplication.getId().value())
                .applicant(applicant)
                .payment(payment)
                .applicantAddress(address)
                .applicantPassportInfo(passportEntity)
                .applicationDate(visaApplication.applicationDate())
                .status(visaApplication.status())
                .intendedArriveDate(visaApplication.intendedArriveDate())
                .purposeOfVisit(visaApplication.purposeOfVisit())
                .build();

    }
}
