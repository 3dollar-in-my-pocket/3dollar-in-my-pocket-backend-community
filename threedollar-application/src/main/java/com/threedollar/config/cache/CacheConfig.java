package com.threedollar.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public List<CaffeineCache> caffeineCaches() {
        return Arrays.stream(CacheType.values())
            .map(cache -> new CaffeineCache(cache.getCacheName(), Caffeine.newBuilder().recordStats()
                .expireAfterWrite(Duration.ofMinutes(cache.getDuration()))
                .maximumSize(cache.getMaxSize())
                .build()))
            .collect(Collectors.toList());
    }

    /**
     *
     * recordState: 캐시에 대한 statics 적용
     * expireAfterWrite: 항목 생성 혹은 업데이트 된 후 기간이 지나면 자동으로 제거되도록 지정
     * maximumSize: 캐시에 포함할 수 있는 최대 엔트리 수 지정
     */

    @Bean
    public CacheManager cacheManager(List<CaffeineCache> caffeineCaches) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caffeineCaches);

        return cacheManager;
    }

}
