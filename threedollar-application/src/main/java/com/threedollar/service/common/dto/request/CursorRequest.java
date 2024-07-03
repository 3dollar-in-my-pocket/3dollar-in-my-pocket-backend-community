package com.threedollar.service.common.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CursorRequest {

    @Nullable
    private Long cursor;

    @Min(0)
    @Max(30)
    private int size = 30;

    public CursorRequest(@Nullable Long cursor, int size) {
        this.cursor = cursor;
        this.size = size;
    }
}
