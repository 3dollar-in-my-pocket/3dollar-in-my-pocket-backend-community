package com.threedollar.config.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheType {

    STICKER(CacheConstants.STICKER, 1, 10000),

    APIKEY(CacheConstants.APIKEY, 1, 10000);

    private final String cacheName; // 이름

    private final int duration; // 캐시 지속 시간

    private final int maxSize; // 캐시에 포함할 수 있는 최대 엔트리 수

    public static class CacheConstants {

        public static final String STICKER = "sticker";

        public static final String APIKEY = "apiKey";

    }


}
