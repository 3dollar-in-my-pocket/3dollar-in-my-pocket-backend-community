package com.threedollar.service.post.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostAndCursorRequest {

    @Nullable
    private Long cursor;

    @Min(0)
    @Max(50)
    private int size;

    @Nullable
    private String accountId;

    public PostAndCursorRequest(Long cursor, int size, String accountId) {
        this.cursor = cursor;
        this.size = size;
        this.accountId = accountId;
    }
}
