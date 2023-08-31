package com.krakozhia.visa.visaApplication.domain.model;

import com.krakozhia.visa.common.ID;

public class PassportInfoId implements ID{

    private Long id;

    public PassportInfoId(Long id) {
        this.id = id;
    }

    @Override
    public Long value() {
        return id;
    }
}
