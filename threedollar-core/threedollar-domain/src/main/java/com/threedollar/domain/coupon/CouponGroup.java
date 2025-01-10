package com.threedollar.domain.coupon;

import lombok.Getter;

@Getter
public enum CouponGroup {
    BOSS_STORE("사장님 가게")
    ;
    private final String description;
    CouponGroup(String description) {
        this.description = description;
    }
}
