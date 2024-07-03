package com.threedollar.service.post.dto.request;


import com.threedollar.domain.post.Post;
import com.threedollar.domain.post.PostGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostAddRequest {

    private Long parentId;

    private String title;

    @NotBlank
    private String content;

    @NotNull
    private List<@Valid PostSectionRequest> sections;

    @Builder
    public PostAddRequest(Long parentId, String title, String content, List<PostSectionRequest> sections) {
        this.parentId = parentId;
        this.title = title;
        this.content = content;
        this.sections = sections;
    }

    public Post toEntity(PostGroup postGroup, String workspaceId, String accountId, String targetId) {

        Post post = Post.of(postGroup, parentId, workspaceId, title, content, targetId, accountId);

        for (PostSectionRequest section : sections) {
            post.add(section.toEntity(post));
        }
        return post;

    }


}
