package com.threedollar.service.post;

import com.threedollar.common.exception.NotFoundException;
import com.threedollar.domain.post.Post;
import com.threedollar.domain.post.PostGroup;
import com.threedollar.domain.post.repository.PostRepository;
import com.threedollar.service.post.request.PostAddRequest;
import com.threedollar.service.post.request.PostUpdateRequest;
import com.threedollar.service.post.response.PostAndCursorResponse;
import com.threedollar.service.post.response.PostResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    @Transactional
    public Long addPost(
        PostGroup postGroup,
        String targetId,
        PostAddRequest request,
        @NotBlank String workspaceId,
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

        Post post = validatePost(workspaceId, accountId, postId, postGroup, targetId);
        post.delete();

    }

    @Transactional(readOnly = true)
    public PostAndCursorResponse getPostsAndCursor(PostGroup postGroup,
                                                   String workspaceId,
                                                   String targetId,
                                                   String accountId,
                                                   Long cursor,
                                                   int size) {
        List<Post> posts = postRepository.findByPostGroupAndWorkspaceIdAndTargetIdAndCursorAndSize(postGroup, workspaceId, targetId, cursor, size + 1);

        if (posts.isEmpty() || posts.size() <= size) {
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
        Post post = validatePost(workspaceId, accountId, postId, postGroup, targetId);
        return PostResponse.of(post, isOwner(post, accountId));
    }

    public Long getPostCountByTargetId(String workspaceId,
                                       PostGroup postGroup,
                                       String targetId) {
        return postRepository.postCountByWorkspaceIdAndPostGroupAndTargetId(workspaceId, postGroup, targetId);
    }


    @Transactional
    public PostResponse update(String workspaceId,
                               String accountId,
                               Long postId,
                               PostGroup postGroup,
                               String targetId,
                               PostUpdateRequest request) {

        Post post = validatePost(workspaceId, accountId, postId, postGroup, targetId);
        post.update(request.getTitle(), request.getContent(), request.getPostSections().stream()
            .map(r -> r.toEntity(post))
            .collect(Collectors.toList()));
        return PostResponse.of(post, isOwner(post, accountId));
    }


    private Post validatePost(String workspaceId,
                              String accountId,
                              Long postId,
                              PostGroup postGroup,
                              String targetId) {
        Post post = postRepository.findByIdAndWorkspaceIdAndAccountIdAndGroupAndTargetId(postId, accountId, workspaceId, postGroup, targetId);
        if (post == null) {
            throw new NotFoundException(String.format("(%s)에 해당하는 postId 는 존재하지 않거나 잘못된 접근입니다", postId));
        }
        return post;

    }

    private static Boolean isOwner(Post post, String accountId) {
        if (accountId == null) {
            return null;
        }
        return Objects.equals(post.getAccountId(), accountId);
    }

}
