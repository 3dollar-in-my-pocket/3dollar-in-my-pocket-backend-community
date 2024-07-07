package com.threedollar.service.post.response;

import com.threedollar.domain.post.SectionType;
import com.threedollar.domain.post.postsection.PostSection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostSectionResponse {

    private SectionType sectionType;

    private String url;

    private double ratio;

    @Builder
    public PostSectionResponse(SectionType sectionType, String url, double ratio) {
        this.sectionType = sectionType;
        this.url = url;
        this.ratio = ratio;
    }

    public static PostSectionResponse of(PostSection section) {

        return PostSectionResponse.builder()
            .sectionType(section.getSectionType())
            .url(section.getUrl())
            .ratio(section.getRatio())
            .build();

    }
}
