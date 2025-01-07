package com.threedollar.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class CouponTime {

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    public CouponTime(LocalDateTime startTime, LocalDateTime endTime) {
        validateCouponTime(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void validateCouponTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("startTime is after endTime");
        }
    }
}
