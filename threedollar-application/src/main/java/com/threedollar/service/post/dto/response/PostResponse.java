package com.threedollar.service.post.dto.response;

import com.threedollar.domain.post.Post;
import com.threedollar.service.common.dto.response.AuditTimeTimeResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResponse extends AuditTimeTimeResponse {

    private Long postId;

    private Long parentId;

    private String title;

    private String content;

    private String accountId;

    private Boolean isOwner;

    private List<PostSectionResponse> sections;

    @Builder
    public PostResponse(Long postId, Long parentId, String title, String content, String accountId, boolean isOwner, List<PostSectionResponse> sections) {
        this.postId = postId;
        this.parentId = parentId;
        this.title = title;
        this.content = content;
        this.accountId = accountId;
        this.isOwner = isOwner;
        this.sections = sections;
    }

    public static PostResponse of(Post post, String accountId) {
        PostResponse response = PostResponse.builder()
            .postId(post.getId())
            .parentId(post.getParentId())
            .title(post.getTitle())
            .content(post.getContent())
            .accountId(post.getAccountId())
            .isOwner(post.isOwner(accountId))
            .sections(getSections(post))
            .build();
        response.setAuditingTime(post);
        return response;
    }

    private static List<PostSectionResponse> getSections(Post post) {
        return post.getPostSection().stream()
            .map(PostSectionResponse::of)
            .collect(Collectors.toList());
    }

}
