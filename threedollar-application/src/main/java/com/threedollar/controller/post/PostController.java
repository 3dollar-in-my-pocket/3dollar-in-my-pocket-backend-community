package com.threedollar.controller.post;

import com.threedollar.common.dto.response.ApiResponse;
import com.threedollar.config.interceptor.ApiKeyContext;
import com.threedollar.config.resolver.RequestApiKey;
import com.threedollar.domain.post.PostGroup;
import com.threedollar.service.post.PostFacadeService;
import com.threedollar.service.post.request.GetPostRequest;
import com.threedollar.service.post.request.PostAddRequest;
import com.threedollar.service.post.request.PostAndCursorRequest;
import com.threedollar.service.post.request.PostUpdateRequest;
import com.threedollar.service.post.response.PostAndCursorResponse;
import com.threedollar.service.post.response.PostResponse;
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

    private final PostFacadeService postFacadeService;

    @Operation(summary = "[소식] 소식을 작성합니다.", description = "유저 혹은 사장님이 소식을 작성합니다.")
    @PostMapping("/v1/post")
    public ApiResponse<Long> addPost(@RequestApiKey ApiKeyContext workspaceId,
                                     @RequestParam String accountId,
                                     @Valid @RequestBody PostAddRequest request) {

        return ApiResponse.success(postFacadeService.addPost(request, workspaceId.getWorkspaceId(), accountId));

    }

    @Operation(summary = "[소식] 소식을 제거합니다.", description = "유저 혹은 사장님이 소식을 제거합니다.")
    @DeleteMapping("/v1/post-group/{postGroup}/post/{postId}")
    public ApiResponse<String> deletePost(@PathVariable Long postId,
                                          @RequestApiKey ApiKeyContext workspaceId,
                                          @PathVariable PostGroup postGroup,
                                          @Valid GetPostRequest request) {
        postFacadeService.deletePost(workspaceId.getWorkspaceId(), request.getAccountId(), postId, postGroup, request.getTargetId());
        return ApiResponse.OK;
    }

    @Operation(summary = "[소식] 소식들을 조회합니다.", description = "다건의 소식들을 조회합니다.")
    @GetMapping("/v1/post-group/{postGroup}")
    public ApiResponse<PostAndCursorResponse> getPosts(@Valid PostAndCursorRequest request,
                                                       @RequestApiKey ApiKeyContext workspaceId,
                                                       @PathVariable PostGroup postGroup) {
        return ApiResponse.success(postFacadeService.getPostAndCursor(request, workspaceId.getWorkspaceId(), postGroup));
    }

    @Operation(summary = "[소식] 소식을 조회합니다", description = "단건의 소식을 조회합니다.")
    @GetMapping("/v1/post-group/{postGroup}/post/{postId}")
    public ApiResponse<PostResponse> getPost(@RequestParam(required = false) String accountId,
                                             @RequestParam String targetId,
                                             @RequestApiKey ApiKeyContext workspaceId,
                                             @PathVariable PostGroup postGroup,
                                             @PathVariable Long postId) {
        return ApiResponse.success(postFacadeService.getPostById(workspaceId.getWorkspaceId(),
            accountId, postId, postGroup, targetId));

    }

    @Operation(summary = "[소식] target 에 해당하는 소식의 수를 조회합니다.", description = "store 등에 해당하는 소식 수를 조회합니다.")
    @GetMapping("/v1/post-group/{postGroup}/count")
    public ApiResponse<Long> getPostCount(@PathVariable PostGroup postGroup,
                                          @RequestApiKey ApiKeyContext workspaceId,
                                          @RequestParam String targetId) {
        return ApiResponse.success(postFacadeService.getPostCountByTargetId(workspaceId.getWorkspaceId(), postGroup, targetId));

    }

    @Operation(summary = "[소식] 소식을 수정합니다.", description = "postId에 해당하는 소식을 수정합니다.")
    @PatchMapping("/v1/post-group/{postGroup}/post/{postId}")
    public ApiResponse<PostResponse> updatePost(@Valid PostUpdateRequest request,
                                        @RequestApiKey ApiKeyContext workspaceId,
                                        @PathVariable PostGroup postGroup,
                                        @PathVariable Long postId,
                                        @RequestParam(required = false) String accountId,
                                        @RequestParam String targetId) {
        return ApiResponse.success(postFacadeService.updatePost(workspaceId.getWorkspaceId(), accountId, postGroup, postId, targetId, request));

    }


}
