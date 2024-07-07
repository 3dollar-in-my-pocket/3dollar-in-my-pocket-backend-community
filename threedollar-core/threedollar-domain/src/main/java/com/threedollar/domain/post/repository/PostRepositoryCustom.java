package com.threedollar.domain.post.repository;

import com.threedollar.domain.post.Post;
import com.threedollar.domain.post.PostGroup;
import jakarta.annotation.Nullable;

import java.util.List;

public interface PostRepositoryCustom {

    Post findByIdAndWorkspaceIdAndGroupAndTargetId(
        String workspaceId,
        PostGroup group,
        String targetId,
        Long postId
    );

    Post findByIdAndWorkspaceIdAndAccountIdAndGroupAndTargetId(Long postId,
                                                               @Nullable String accountId,
                                                               String workspaceId,
                                                               PostGroup group,
                                                               String targetId);

    List<Post> findByPostGroupAndWorkspaceIdAndTargetIdAndCursorAndSize(PostGroup postGroup,
                                                                        String workspaceId,
                                                                        String targetId,
                                                                        @Nullable Long cursor,
                                                                        int size);

    long postCountByWorkspaceIdAndPostGroupAndTargetId(String workspaceId,
                                                       PostGroup postGroup,
                                                       String targetId);
}
