package com.threedollar.service.post.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PostUpdateRequest {

    @Nullable
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private List<PostSectionRequest> sections;

    @Builder
    public PostUpdateRequest(String title, String content, List<PostSectionRequest> sections) {
        this.title = title;
        this.content = content;
        this.sections = sections;
    }

}
