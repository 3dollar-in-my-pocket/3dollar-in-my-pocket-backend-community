package com.threedollar.service.post;

import com.threedollar.domain.post.PostGroup;
import com.threedollar.service.post.request.PostAddRequest;
import com.threedollar.service.post.request.PostAndCursorRequest;
import com.threedollar.service.post.request.PostUpdateRequest;
import com.threedollar.service.post.response.PostAndCursorResponse;
import com.threedollar.service.post.response.PostResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostFacadeService {

    private final PostService postService;

    public Long addPost(PostAddRequest request,
                        @NotBlank String workspaceId,
                        @NotBlank String accountId) {
        return postService.addPost(request, workspaceId, accountId);
    }


    public void deletePost(@NotBlank String workspaceId,
                           @NotBlank String accountId,
                           @NotNull Long postId,
                           @NotNull PostGroup postGroup,
                           @NotBlank String targetId) {

        postService.deletePost(workspaceId, accountId, postId, postGroup, targetId);

    }

    public PostAndCursorResponse getPostAndCursor(@Valid PostAndCursorRequest request,
                                                  String workspaceId,
                                                  PostGroup postGroup) {
        return postService.getPostsAndCursor(
            postGroup,
            workspaceId,
            request.getTargetId(),
            request.getAccountId(),
            request.getCursor(),
            request.getSize());
    }


    public PostResponse getPostById(String workspaceId,
                                    String accountId,
                                    Long postId,
                                    PostGroup postGroup,
                                    String targetId) {
        return postService.getPostById(workspaceId, accountId, postId, postGroup, targetId);
    }

    public Long getPostCountByTargetId(String workspaceId,
                                       PostGroup postGroup,
                                       String targetId) {
        return postService.getPostCountByTargetId(workspaceId, postGroup, targetId);
    }

    public Long updatePost(String workspaceId,
                           String accountId,
                           PostGroup postGroup,
                           Long postId,
                           String targetId,
                           PostUpdateRequest request) {
        return postService.update(workspaceId, accountId, postId, postGroup, targetId, request);

    }

}
