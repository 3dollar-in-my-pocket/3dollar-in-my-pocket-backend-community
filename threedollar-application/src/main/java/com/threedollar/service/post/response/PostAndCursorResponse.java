package com.threedollar.service.post.response;

import com.threedollar.domain.post.Post;
import com.threedollar.common.dto.response.CursorResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class PostAndCursorResponse {

    private CursorResponse cursor;

    private List<PostResponse> posts;

    @Builder
    public PostAndCursorResponse(CursorResponse cursor, List<PostResponse> posts) {
        this.cursor = cursor;
        this.posts = posts;
    }

    public static PostAndCursorResponse hasMore(List<Post> posts, String accountId) {
        return PostAndCursorResponse.builder()
            .cursor(CursorResponse.builder()
                .hasNext(true)
                .hasMore(true)
                .nextCursor(posts.get(posts.size() - 2).getId())
                .build())
            .posts(getPostResponse(posts.subList(0, posts.size() - 1), accountId))
            .build();
    }

    public static PostAndCursorResponse noMore(List<Post> posts, String accountId) {
        return PostAndCursorResponse.builder()
            .cursor(CursorResponse.builder()
                .hasNext(false)
                .hasMore(false)
                .nextCursor(null)
                .build())
            .posts(getPostResponse(posts, accountId))
            .build();
    }

    private static List<PostResponse> getPostResponse(List<Post> posts, String accountId) {
        return posts.stream()
            .map(post -> PostResponse.of(post, accountId))
            .collect(Collectors.toList());
    }

}
