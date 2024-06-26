package com.threedollar.service.post.response;

import com.threedollar.domain.post.Post;
import com.threedollar.service.poll.dto.response.CursorResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
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

    public static PostAndCursorResponse hasNext(List<Post> posts, String accountId) {
        return PostAndCursorResponse.builder()
            .cursor(CursorResponse.builder()
                .hasNext(true)
                .nextCursor(posts.get(posts.size() - 2).getId())
                .build())
            .posts(getPostResponse(posts.subList(0, posts.size() - 1), accountId))
            .build();
    }

    public static PostAndCursorResponse noMore(List<Post> posts, String accountId) {
        return PostAndCursorResponse.builder()
            .cursor(CursorResponse.builder()
                .hasNext(false)
                .nextCursor(null)
                .build())
            .posts(getPostResponse(posts, accountId))
            .build();
    }

    private static List<PostResponse> getPostResponse(List<Post> posts, String accountId) {
        return posts.stream()
            .map(post -> PostResponse.of(post, isOwner(post, accountId)))
            .collect(Collectors.toList());
    }

    private static boolean isOwner(Post post, String accountId) {
        return Objects.equals(post.getAccountId(), accountId);
    }


}
