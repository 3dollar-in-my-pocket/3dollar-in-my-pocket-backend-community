package com.threedollar.service.post.request;

import com.threedollar.domain.post.PostGroup;

import jakarta.annotation.Nullable;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostCountRequest {

    @Nullable
    private LocalDateTime startTime;

    @Nullable
    private LocalDateTime endTime;

    @Nullable
    private String targetId;

    @Nullable
    private String workspaceId;

    @Nullable
    private PostGroup postGroup;

    public PostCountRequest(@Nullable LocalDateTime startTime, @Nullable LocalDateTime endTime, @Nullable String targetId,
        @Nullable String workspaceId, @Nullable PostGroup postGroup) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.targetId = targetId;
        this.workspaceId = workspaceId;
        this.postGroup = postGroup;
    }
}
