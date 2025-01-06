package com.threedollar.domain.coupon;

import lombok.Getter;

@Getter
public enum CouponTag {

    BOSS_EVENT("사장님 이벤트")
    ;

    private final String description;

    CouponTag(String description) {
        this.description = description;
    }
}
