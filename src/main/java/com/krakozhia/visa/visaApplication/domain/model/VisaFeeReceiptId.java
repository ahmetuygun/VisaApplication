package com.krakozhia.visa.visaApplication.domain.model;

import com.krakozhia.visa.common.ID;

public class VisaFeeReceiptId implements ID{

    private Long id;

    public VisaFeeReceiptId(Long id) {
        this.id = id;
    }

    @Override
    public Long value() {
        return id;
    }
}
