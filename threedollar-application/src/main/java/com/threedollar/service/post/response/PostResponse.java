package com.threedollar.service.post.response;

import com.threedollar.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class PostResponse {

    private Long postId;

    private Long parentId;

    private String title;

    private String content;

    private String accountId;

    private Boolean isOwner;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<PostSectionResponse> sections;

    @Builder
    public PostResponse(Long postId, Long parentId, String title, String content, String accountId, boolean isOwner, List<PostSectionResponse> sections, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.parentId = parentId;
        this.title = title;
        this.content = content;
        this.accountId = accountId;
        this.isOwner = isOwner;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.sections = sections;
    }

    public static PostResponse of(Post post, String accountId) {
        return PostResponse.builder()
            .postId(post.getId())
            .parentId(post.getParentId())
            .title(post.getTitle())
            .content(post.getContent())
            .accountId(post.getAccountId())
            .isOwner(post.isOwner(accountId))
            .createdAt(post.getCreatedAt())
            .updatedAt(post.getUpdatedAt())
            .sections(getPostSectionResponses(post))
            .build();
    }

    private static List<PostSectionResponse> getPostSectionResponses(Post post) {
        return post.getPostSection().stream()
            .map(PostSectionResponse::of)
            .collect(Collectors.toList());
    }

}
