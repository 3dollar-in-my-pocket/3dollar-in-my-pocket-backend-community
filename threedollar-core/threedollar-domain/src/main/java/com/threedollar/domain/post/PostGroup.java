package com.threedollar.domain.post;

import lombok.Getter;

@Getter
public enum PostGroup {

    STORE_NEWS("가게 소식")
    ,REVIEW_PRESET_COMMENT("리뷰 답글 자주 사용하는 문구")
    ;

    private final String description;

    PostGroup(String description) {
        this.description = description;
    }

}
