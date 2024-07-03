package com.threedollar.controller.post;

import com.threedollar.common.dto.response.ApiResponse;
import com.threedollar.config.interceptor.ApiKeyContext;
import com.threedollar.config.resolver.RequestApiKey;
import com.threedollar.domain.post.PostGroup;
import com.threedollar.service.common.dto.request.CursorRequest;
import com.threedollar.service.post.PostService;
import com.threedollar.service.post.dto.request.PostAddRequest;
import com.threedollar.service.post.dto.request.PostListRequest;
import com.threedollar.service.post.dto.request.PostUpdateRequest;
import com.threedollar.service.post.dto.response.PostAndCursorResponse;
import com.threedollar.service.post.dto.response.PostResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "[소식] 소식을 작성합니다.", description = "유저 혹은 사장님이 소식을 작성합니다.")
    @PostMapping("/v1/post-group/{postGroup}/target/{targetId}/post")
    public ApiResponse<Long> addPost(@RequestApiKey ApiKeyContext context,
                                     @PathVariable PostGroup postGroup,
                                     @PathVariable String targetId,
                                     @RequestParam String accountId,
                                     @Valid @RequestBody PostAddRequest request) {

        return ApiResponse.success(postService.addPost(context.getWorkspaceId(), postGroup, targetId, request, accountId));

    }

    @Operation(summary = "[소식] 소식을 제거합니다.", description = "유저 혹은 사장님이 소식을 제거합니다.")
    @DeleteMapping("/v1/post-group/{postGroup}/target/{targetId}/post/{postId}")
    public ApiResponse<String> deletePost(@PathVariable Long postId,
                                          @RequestApiKey ApiKeyContext context,
                                          @PathVariable PostGroup postGroup,
                                          @PathVariable String targetId,
                                          @RequestParam String accountId) {
        postService.deletePost(context.getWorkspaceId(), accountId, postId, postGroup, targetId);
        return ApiResponse.OK;
    }

    @Operation(summary = "[소식] 소식들을 조회합니다.", description = "다건의 소식들을 조회합니다.")
    @GetMapping("/v1/post-group/{postGroup}/target/{targetId}/posts")
    public ApiResponse<PostAndCursorResponse> getPosts(@Valid CursorRequest cursorRequest,
                                                       @Valid PostListRequest request,
                                                       @RequestApiKey ApiKeyContext context,
                                                       @PathVariable String targetId,
                                                       @PathVariable PostGroup postGroup) {
        return ApiResponse.success(postService.getPostsAndCursor(postGroup, context.getWorkspaceId(), targetId, request.getAccountId(), cursorRequest));
    }

    @Operation(summary = "[소식] 소식을 조회합니다", description = "단건의 소식을 조회합니다.")
    @GetMapping("/v1/post-group/{postGroup}/target/{targetId}/post/{postId}")
    public ApiResponse<PostResponse> getPost(@RequestParam(required = false) String accountId,
                                             @RequestApiKey ApiKeyContext context,
                                             @PathVariable PostGroup postGroup,
                                             @PathVariable String targetId,
                                             @PathVariable Long postId) {
        return ApiResponse.success(postService.getPostById(context.getWorkspaceId(),
            accountId, postId, postGroup, targetId));

    }

    @Operation(summary = "[소식] target 에 해당하는 소식의 수를 조회합니다.", description = "store 등에 해당하는 소식 수를 조회합니다.")
    @GetMapping("/v1/post-group/{postGroup}/target/{targetId}/post-count")
    public ApiResponse<Long> getPostCount(@PathVariable PostGroup postGroup,
                                          @RequestApiKey ApiKeyContext context,
                                          @PathVariable String targetId) {
        return ApiResponse.success(postService.getPostCountByTargetId(context.getWorkspaceId(), postGroup, targetId));

    }

    @Operation(summary = "[소식] 소식을 수정합니다.", description = "postId에 해당하는 소식을 수정합니다.")
    @PatchMapping("/v1/post-group/{postGroup}/target/{targetId}/post/{postId}")
    public ApiResponse<PostResponse> updatePost(@Valid @RequestBody PostUpdateRequest request,
                                                @RequestApiKey ApiKeyContext context,
                                                @PathVariable PostGroup postGroup,
                                                @PathVariable Long postId,
                                                @RequestParam(required = false) String accountId,
                                                @PathVariable String targetId) {

        return ApiResponse.success(postService.update(context.getWorkspaceId(), accountId, postId, postGroup, targetId, request));

    }


}
