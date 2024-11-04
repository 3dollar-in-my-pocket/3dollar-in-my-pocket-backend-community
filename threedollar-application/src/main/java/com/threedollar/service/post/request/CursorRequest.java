package com.threedollar.service.post.request;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CursorRequest {

    @Nullable
    private Long cursor;

    @Min(0)
    @Max(30)
    private int size;

    public CursorRequest(@Nullable Long cursor, int size) {
        this.cursor = cursor;
        this.size = size;
    }

}
