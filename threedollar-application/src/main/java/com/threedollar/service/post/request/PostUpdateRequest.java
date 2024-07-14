package com.threedollar.service.post.request;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class PostUpdateRequest {

    @Nullable
    private String title;

    @Nullable
    private String content;

    @Nullable
    private List<PostSectionRequest> sections;

    @Builder
    public PostUpdateRequest(@Nullable String title, @Nullable String content, @Nullable List<PostSectionRequest> sections) {
        this.title = title;
        this.content = content;
        this.sections = sections;
    }
}
