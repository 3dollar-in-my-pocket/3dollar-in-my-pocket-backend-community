package com.threedollar.service.post.request;


import com.threedollar.domain.post.Post;
import com.threedollar.domain.post.PostGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostAddRequest {

    private Long parentId;

    private String title;

    @NotBlank
    private String content;

    @NotNull
    private List<PostSectionRequest> sections;

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
