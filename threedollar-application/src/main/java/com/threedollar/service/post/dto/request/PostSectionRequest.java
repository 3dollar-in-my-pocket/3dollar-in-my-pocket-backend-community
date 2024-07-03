package com.threedollar.service.post.dto.request;

import com.threedollar.domain.post.Post;
import com.threedollar.domain.post.section.SectionType;
import com.threedollar.domain.post.section.PostSection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PostSectionRequest {

    @NotNull
    private SectionType sectionType;

    @NotBlank
    private String url;

    private double ratio;

    @Builder
    public PostSectionRequest(SectionType sectionType, String url, double ratio) {
        this.sectionType = sectionType;
        this.url = url;
        this.ratio = ratio;
    }

    public PostSection toEntity(Post post) {
        return PostSection.of(sectionType, post, url, ratio);
    }

}
