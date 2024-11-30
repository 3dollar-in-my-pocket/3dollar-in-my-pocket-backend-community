package com.threedollar.domain.post.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.threedollar.domain.post.Post;
import com.threedollar.domain.post.PostGroup;
import com.threedollar.domain.post.PostStatus;

import java.time.LocalDateTime;

import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

import static com.threedollar.domain.post.QPost.post;
import static com.threedollar.domain.post.postsection.QPostSection.postSection;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Post findByIdAndWorkspaceIdAndGroupAndTargetId(String workspaceId, PostGroup group, String targetId, Long postId) {
        return jpaQueryFactory.selectFrom(post)
            .leftJoin(post.postSection, postSection).fetchJoin()
            .where(
                post.workspaceId.eq(workspaceId),
                post.id.eq(postId),
                post.postGroup.eq(group),
                post.targetId.eq(targetId),
                post.status.eq(PostStatus.ACTIVE)
            )
            .fetchOne();
    }

    @Override
    public Post findByIdAndWorkspaceIdAndAccountIdAndGroupAndTargetId(Long postId, String accountId, String workspaceId, PostGroup postGroup, String targetId) {
        return jpaQueryFactory.selectFrom(post)
            .leftJoin(post.postSection, postSection).fetchJoin()
            .where(
                existsAccountId(accountId),
                post.workspaceId.eq(workspaceId),
                post.id.eq(postId),
                post.postGroup.eq(postGroup),
                post.targetId.eq(targetId),
                post.status.eq(PostStatus.ACTIVE)
            )
            .fetchOne();
    }

    @Override
    public List<Post> findByPostGroupAndWorkspaceIdAndTargetIdAndCursorAndSizeDesc(PostGroup postGroup, String workspaceId, String targetId, Long cursor, int size) {
        List<Long> postIds = jpaQueryFactory.select(post.id)
            .from(post)
            .where(
                existsCursor(cursor),
                post.workspaceId.eq(workspaceId),
                post.postGroup.eq(postGroup),
                post.targetId.eq(targetId),
                post.status.eq(PostStatus.ACTIVE)
            )
            .orderBy(post.id.desc())
            .limit(size)
            .fetch();

        return jpaQueryFactory.selectFrom(post)
            .where(post.id.in(postIds))
            .leftJoin(post.postSection, postSection).fetchJoin()
            .orderBy(post.id.desc())
            .fetch();
    }

    @Override
    public List<Post> findByPostGroupAndWorkspaceIdAndTargetIdAndCursorAndSizeAsc(
        PostGroup postGroup,
        String workspaceId, String targetId, Long cursor, int size) {
        List<Long> postIds = jpaQueryFactory.select(post.id)
            .from(post)
            .where(
                post.id.gt(cursor),
                post.workspaceId.eq(workspaceId),
                post.postGroup.eq(postGroup),
                post.targetId.eq(targetId),
                post.status.eq(PostStatus.ACTIVE)
            )
            .orderBy(post.id.asc())
            .limit(size)
            .fetch();

        return jpaQueryFactory.selectFrom(post)
            .where(post.id.in(postIds))
            .leftJoin(post.postSection, postSection).fetchJoin()
            .orderBy(post.id.asc())
            .fetch();
    }


    @Override
    public long postCountByWorkspaceIdAndPostGroupAndTargetIdAndStartTimeAndEndTime(String workspaceId, PostGroup postGroup, String targetId,
        LocalDateTime startTime, LocalDateTime endTime) {
        Long count = jpaQueryFactory.select(post.count())
            .from(post)
            .where(
                existsWorkspaceId(workspaceId),
                existsPostGroup(postGroup),
                existsTargetId(targetId),
                existsStartTime(startTime),
                existsEndTime(endTime),
                post.status.eq(PostStatus.ACTIVE)
            ).fetchOne();
        return ObjectUtils.defaultIfNull(count, 0L);
    }


    @Override
    public boolean existPostByPostGroupAndPostIdAndTargetId(PostGroup postGroup, Long postId, String targetId) {

        Integer fetchOne = jpaQueryFactory.selectOne()
            .from(post)
            .where(
                post.postGroup.eq(postGroup),
                post.id.eq(postId),
                post.targetId.eq(targetId),
                post.status.eq(PostStatus.ACTIVE)
            ).fetchFirst();
        return fetchOne != null;
    }

    private BooleanExpression existsCursor(Long cursor) {
        if (cursor == null) {
            return null;
        }
        return post.id.lt(cursor);
    }

    private BooleanExpression existsAccountId(String accountId) {
        if (accountId == null) {
            return null;
        }
        return post.accountId.eq(accountId);
    }


    private BooleanExpression existsStartTime(LocalDateTime startTime) {
        return post.createdAt.goe(
            Objects.requireNonNullElseGet(startTime, () -> LocalDateTime.of(1970, 1, 1, 0, 0)));
    }

    private BooleanExpression existsEndTime(LocalDateTime endTime) {
        return post.createdAt.loe(Objects.requireNonNullElseGet(endTime, LocalDateTime::now));

    }

    private BooleanExpression existsTargetId(String targetId) {
        if (targetId == null) {
            return null;
        }
        return post.targetId.eq(targetId);
    }

    private BooleanExpression existsWorkspaceId(String workspaceId) {
        if (workspaceId == null) {
            return null;
        }
        return post.workspaceId.eq(workspaceId);
    }

    private BooleanExpression existsPostGroup(PostGroup postGroup) {
        if (postGroup == null) {
            return null;
        }
        return post.postGroup.eq(postGroup);
    }


}
