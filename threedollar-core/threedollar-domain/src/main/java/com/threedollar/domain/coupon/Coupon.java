package com.threedollar.domain.coupon;

import com.threedollar.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;

@Entity
@Getter
@NoArgsConstructor
@Table(uniqueConstraints = {
    @UniqueConstraint(
        name = "uni_coupon_1",
        columnNames = {}
    )
})
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

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private String accountId;

    @Builder(access = AccessLevel.PRIVATE)
    public Coupon(String workspaceId, String targetId, String name, CouponType couponType,
        CouponTag couponTag, CouponStatus status, long count, LocalDateTime startTime, LocalDateTime endTime,
        String accountId) {
        this.workspaceId = workspaceId;
        this.targetId = targetId;
        this.name = name;
        this.couponTag = couponTag;
        this.couponType = couponType;
        this.status = status;
        this.count = count;
        this.startTime = startTime;
        this.endTime = endTime;
        this.accountId = accountId;
    }

    public static Coupon newInstance(String workspaceId, String targetId, String name,
        CouponType couponType, CouponTag couponTag, long count, LocalDateTime startTime, LocalDateTime endTime) {
        return Coupon.builder()
            .couponType(couponType)
            .workspaceId(workspaceId)
            .targetId(targetId)
            .name(name)
            .couponTag(couponTag)
            .count(count)
            .status(CouponStatus.ACTIVE)
            .startTime(startTime)
            .endTime(endTime)
            .build();
    }
}
