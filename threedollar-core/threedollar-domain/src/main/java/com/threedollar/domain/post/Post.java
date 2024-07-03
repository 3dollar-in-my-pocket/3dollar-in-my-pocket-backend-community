package com.threedollar.domain.post;

import com.threedollar.domain.BaseEntity;
import com.threedollar.domain.post.section.PostSection;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseEntity {

    @Column(nullable = false, length = 30, columnDefinition = "VARCHAR(30)")
    @Enumerated(EnumType.STRING)
    private PostGroup postGroup;

    @Column(length = 200)
    private Long parentId;

    @Column(nullable = false, length = 50)
    private String workspaceId;

    @Column(length = 100)
    private String title;

    @Column(length = 3000)
    private String content;

    @Column(nullable = false, length = 50)
    private String targetId;

    @Column(nullable = false, length = 50)
    private String accountId;

    @Column(nullable = false, length = 30, columnDefinition = "VARCHAR(30)")
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostSection> postSection = new ArrayList<>();

    @Builder
    public Post(PostGroup postGroup, Long parentId, String workspaceId, String title, String content, String targetId, String accountId, PostStatus status) {
        this.postGroup = postGroup;
        this.parentId = parentId;
        this.workspaceId = workspaceId;
        this.title = title;
        this.content = content;
        this.targetId = targetId;
        this.accountId = accountId;
        this.status = status;
    }

    public void add(PostSection postSection) {
        this.postSection.add(postSection);
    }

    public void update(String title, String content, List<PostSection> sections) {
        if (sections != null) {
            this.postSection = sections;
        }
        if (title != null) {
            this.title = title;
        }
        if (content != null) {
            this.content = content;
        }
    }

    public void delete() {
        this.status = PostStatus.DELETED;
    }

    public static Post of(PostGroup postGroup, Long parentId, String workspaceId,
                          String title, String content, String targetId, String accountId) {
        return Post.builder()
            .postGroup(postGroup)
            .parentId(parentId)
            .workspaceId(workspaceId)
            .title(title)
            .content(content)
            .targetId(targetId)
            .accountId(accountId)
            .status(PostStatus.ACTIVE)
            .build();
    }

    public boolean isOwner(String accountId) {
        if (StringUtils.isBlank(accountId)) {
            return false;
        }
        return this.accountId.equals(accountId);
    }


}
