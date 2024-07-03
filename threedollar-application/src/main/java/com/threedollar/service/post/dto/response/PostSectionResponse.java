package com.threedollar.service.post.dto.response;

import com.threedollar.domain.post.section.SectionType;
import com.threedollar.domain.post.section.PostSection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
