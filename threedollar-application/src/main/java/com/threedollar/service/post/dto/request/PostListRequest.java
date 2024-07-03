package com.threedollar.service.post.dto.request;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PostListRequest {

    @Nullable
    private String accountId;

    public PostListRequest(@Nullable String accountId) {
        this.accountId = accountId;
    }

}
