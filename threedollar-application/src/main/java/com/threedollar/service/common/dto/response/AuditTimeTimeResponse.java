package com.threedollar.service.common.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.threedollar.domain.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AuditTimeTimeResponse {

    @NotNull
    protected LocalDateTime createdAt;

    @NotNull
    protected LocalDateTime updatedAt;

    @JsonIgnore
    protected void setAuditingTime(BaseEntity baseEntity) {
        this.createdAt = baseEntity.getCreatedAt();
        this.updatedAt = baseEntity.getUpdatedAt();
    }

}
