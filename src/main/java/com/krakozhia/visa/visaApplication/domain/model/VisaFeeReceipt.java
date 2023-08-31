package com.krakozhia.visa.visaApplication.domain.model;

import java.math.BigDecimal;


public class VisaFeeReceipt {
    private VisaFeeReceiptId visaFeeReceiptId;
    private BigDecimal chargedAmount;
    public Boolean valid;

    public VisaFeeReceipt(VisaFeeReceiptId visaFeeReceiptId, BigDecimal chargedAmount, Boolean valid) {
        this.visaFeeReceiptId = visaFeeReceiptId;
        this.chargedAmount = chargedAmount;
        this.valid = valid;
    }

    public VisaFeeReceiptId id() {
        return visaFeeReceiptId;
    }

    public BigDecimal chargedAmount() {
        return chargedAmount;
    }

    public Boolean valid() {
        return valid;
    }
}
