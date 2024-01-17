package com.threedollar.domain.redis.sticker;

import com.threedollar.domain.redis.StringRedisRepository;
import com.threedollar.domain.redis.sticker.repository.StickerCountRepository;
import com.threedollar.domain.sticker.StickerGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class StickerCountRepositoryImpl implements StickerCountRepository {

    private final StringRedisRepository<StickerCountKey, Long> stickerRedisRepository;

    @Override
    public void incrByCount(StickerGroup stickerGroup, String targetId, Long stickerId) {
        StickerCountKey key = StickerCountKey.builder()
                .stickerGroup(stickerGroup)
                .targetId(targetId)
                .build();
        stickerRedisRepository.incr(key);
    }

    @Override
    public long getValueByKey(StickerGroup stickerGroup, String targetId, Long stickerId) {
        StickerCountKey key = StickerCountKey.builder()
                .stickerGroup(stickerGroup)
                .targetId(targetId)
                .build();
        return stickerRedisRepository.get(key);
    }

    @Override
    public void decrByCount(StickerGroup stickerGroup, String targetId, Long stickerId) {
        StickerCountKey key = StickerCountKey.builder()
                .stickerGroup(stickerGroup)
                .targetId(targetId)
                .build();
        stickerRedisRepository.decr(key);
    }

    @Override
    public void incrBulkByCount(StickerGroup stickerGroup, String targetId, List<Long> stickerIds) {
        List<StickerCountKey> stickerCountKeys = stickerIds.stream()
                .map(id -> StickerCountKey.builder()
                        .stickerId(id)
                        .targetId(targetId)
                        .stickerGroup(stickerGroup)
                        .build())
                .toList();
        stickerRedisRepository.incrBulk(stickerCountKeys);

    }

    @Override
    public void decrBulkByCount(StickerGroup stickerGroup, String targetId, List<Long> stickerIds) {
        List<StickerCountKey> stickerCountKeys = stickerIds.stream()
                .map(id -> StickerCountKey.builder()
                        .stickerId(id)
                        .targetId(targetId)
                        .stickerGroup(stickerGroup)
                        .build())
                .toList();
        stickerRedisRepository.decrBulk(stickerCountKeys);
    }

    @Override
    public List<Long> getValuesByKeys(StickerGroup stickerGroup, String targetId, List<Long> stickerIds) {
        List<StickerCountKey> stickerCountKeys = stickerIds.stream()
                .map(id -> StickerCountKey.builder()
                        .stickerId(id)
                        .targetId(targetId)
                        .stickerGroup(stickerGroup)
                        .build())
                .toList();
        return stickerRedisRepository.getBulk(stickerCountKeys);
    }

    @Override
    public Map<StickerCountKey, Long> stickerCount(List<StickerCountKey> stickerCountKeys) {
        Map<StickerCountKey, Long> result = new HashMap<>();
        for (StickerCountKey stickerCountKey : stickerCountKeys) {
            Long count = stickerRedisRepository.get(stickerCountKey);
            result.put(stickerCountKey, count);
        }
        return result;
    }
}