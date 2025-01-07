package com.threedollar.domain.coupon;

import com.threedollar.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String workspaceId;

    @Column(nullable = false)
    private String targetId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponType couponType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponTag couponTag;

    @Column(nullable = false)
    private long count;

    @Embedded
    private CouponTime couponTime;

    @Column(nullable = false)
    private String accountId;

    @Builder(access = AccessLevel.PRIVATE)
    public Coupon(String workspaceId, String targetId, String name, CouponType couponType,
        CouponTag couponTag, CouponStatus status, CouponTime couponTime, long count, String accountId) {
        this.workspaceId = workspaceId;
        this.targetId = targetId;
        this.name = name;
        this.couponTag = couponTag;
        this.couponType = couponType;
        this.status = status;
        this.count = count;
        this.couponTime = couponTime;
        this.accountId = accountId;
    }

    public static Coupon newInstance(String workspaceId, String targetId, String name,
        CouponType couponType, CouponTag couponTag, long count, CouponTime couponTime, String accountId) {
        return Coupon.builder()
            .couponType(couponType)
            .workspaceId(workspaceId)
            .targetId(targetId)
            .name(name)
            .couponTag(couponTag)
            .couponTime(couponTime)
            .accountId(accountId)
            .count(count)
            .status(CouponStatus.ACTIVE)
            .build();
    }
}
