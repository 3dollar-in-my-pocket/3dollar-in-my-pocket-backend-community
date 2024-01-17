package com.threedollar.domain.redis.sticker.repository;

import com.threedollar.domain.redis.sticker.StickerCountKey;
import com.threedollar.domain.sticker.StickerGroup;

import java.util.List;
import java.util.Map;

public interface StickerCountRepository {

    void incrByCount(StickerGroup stickerGroup, String targetId, Long stickerId);

    long getValueByKey(StickerGroup stickerGroup, String targetId, Long stickerId);

    void decrByCount(StickerGroup stickerGroup, String targetId, Long stickerId);

    void incrBulkByCount(StickerGroup stickerGroup, String targetId, List<Long> stickerIds);

    void decrBulkByCount(StickerGroup stickerGroup, String targetId, List<Long> stickerIds);

    List<Long> getValuesByKeys(StickerGroup stickerGroup, String targetId, List<Long> stickerIds);

    Map<StickerCountKey, Long> stickerCount(List<StickerCountKey> stickerCountKeys);



}