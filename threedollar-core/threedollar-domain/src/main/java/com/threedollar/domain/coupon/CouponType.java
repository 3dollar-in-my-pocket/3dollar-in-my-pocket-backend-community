package com.threedollar.domain.coupon;

import lombok.Getter;

@Getter
public enum CouponType {
    LIMITED("수량제한"),
    UNLIMITED("무제한"),
    ;
    private final String description;

    CouponType(String description) {
        this.description = description;
    }


}
