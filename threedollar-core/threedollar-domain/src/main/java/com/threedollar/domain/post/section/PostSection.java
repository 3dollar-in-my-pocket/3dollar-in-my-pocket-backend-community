package com.threedollar.domain.post.section;

import com.threedollar.domain.BaseEntity;
import com.threedollar.domain.post.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostSection extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 200, columnDefinition = "VARCHAR(30)")
    private SectionType sectionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false, length = 500)
    private String url;

    @Column
    private double ratio;

    @Builder
    public PostSection(SectionType sectionType, Post post, double ratio, String url) {
        this.sectionType = sectionType;
        this.post = post;
        this.url = url;
        this.ratio = ratio;
    }

    public static PostSection of(SectionType sectionType, Post post, String url, double ratio) {
        return PostSection.builder()
            .post(post)
            .sectionType(sectionType)
            .url(url)
            .ratio(ratio)
            .build();
    }

}
