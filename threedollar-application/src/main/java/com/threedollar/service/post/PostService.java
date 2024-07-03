package com.threedollar.service.post;

import com.threedollar.common.exception.NotFoundException;
import com.threedollar.domain.post.Post;
import com.threedollar.domain.post.PostGroup;
import com.threedollar.domain.post.repository.PostRepository;
import com.threedollar.service.common.dto.request.CursorRequest;
import com.threedollar.service.post.dto.request.PostAddRequest;
import com.threedollar.service.post.dto.request.PostUpdateRequest;
import com.threedollar.service.post.dto.response.PostAndCursorResponse;
import com.threedollar.service.post.dto.response.PostResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long addPost(
        @NotBlank String workspaceId,
        PostGroup postGroup,
        String targetId,
        PostAddRequest request,
        String accountId) {
        Post post = postRepository.save(request.toEntity(postGroup, workspaceId, accountId, targetId));
        return post.getId();
    }

    @Transactional
    public void deletePost(String workspaceId,
                           String accountId,
                           Long postId,
                           PostGroup postGroup,
                           String targetId) {
        Post post = getPostByIdAndAccountId(workspaceId, postGroup, targetId, postId, accountId);
        post.delete();

    }

    @Transactional(readOnly = true)
    public PostAndCursorResponse getPostsAndCursor(PostGroup postGroup,
                                                   String workspaceId,
                                                   String targetId,
                                                   String accountId,
                                                   CursorRequest cursorRequest) {
        List<Post> posts = postRepository.findByPostGroupAndWorkspaceIdAndTargetIdAndCursorAndSize(postGroup, workspaceId, targetId, cursorRequest.getCursor(), cursorRequest.getSize() + 1);

        if (posts.isEmpty() || posts.size() <= cursorRequest.getSize()) {
            return PostAndCursorResponse.noMore(posts, accountId);
        }
        return PostAndCursorResponse.hasNext(posts, accountId);

    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(String workspaceId,
                                    String accountId,
                                    Long postId,
                                    PostGroup postGroup,
                                    String targetId) {
        Post post = postRepository.findByIdAndWorkspaceIdAndGroupAndTargetId(workspaceId, postGroup, targetId, postId);
        return PostResponse.of(post, accountId);
    }

    public Long getPostCountByTargetId(String workspaceId,
                                       PostGroup postGroup,
                                       String targetId) {
        return Math.max(0, postRepository.postCountByWorkspaceIdAndPostGroupAndTargetId(workspaceId, postGroup, targetId));
    }

    @Transactional
    public PostResponse update(String workspaceId,
                               String accountId,
                               Long postId,
                               PostGroup postGroup,
                               String targetId,
                               PostUpdateRequest request) {

        Post post = getPostByIdAndAccountId(workspaceId, postGroup, targetId, postId, accountId);
        post.update(request.getTitle(), request.getContent(), request.getSections().stream()
            .map(r -> r.toEntity(post))
            .collect(Collectors.toList()));
        return PostResponse.of(post, accountId);
    }

    private Post getPostByIdAndAccountId(String workspaceId,
                                         PostGroup postGroup,
                                         String targetId,
                                         Long postId,
                                         String accountId) {
        Post post = postRepository.findByIdAndWorkspaceIdAndAccountIdAndGroupAndTargetId(postId, accountId, workspaceId, postGroup, targetId);
        if (post == null) {
            throw new NotFoundException(String.format("(%s)에 해당하는 postId 는 계정(%s)의 소유가 아니거나 존재하지 않습니다", postId, accountId));
        }
        return post;

    }

}
