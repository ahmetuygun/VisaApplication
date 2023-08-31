package com.krakozhia.visa.visaApplication.domain.model;

import com.krakozhia.visa.common.ID;

public class AddressId implements ID{

    private Long id;

    public AddressId(Long id) {
        this.id = id;
    }

    @Override
    public Long value() {
        return id;
    }
}
