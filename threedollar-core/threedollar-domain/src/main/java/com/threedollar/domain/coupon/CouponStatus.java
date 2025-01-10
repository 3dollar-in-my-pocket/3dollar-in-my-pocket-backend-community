package com.threedollar.domain.coupon;

import lombok.Getter;

@Getter
public enum CouponStatus {
    ACTIVE("활성화")
    ,DELETED("삭제됨")
    ;

    private final String description;

    CouponStatus(String description) {
        this.description = description;
    }

}
