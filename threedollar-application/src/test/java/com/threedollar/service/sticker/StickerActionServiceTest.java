package com.threedollar.service.sticker;

import com.threedollar.IntegrationTest;
import com.threedollar.domain.sticker.Sticker;
import com.threedollar.domain.sticker.StickerGroup;
import com.threedollar.domain.sticker.repository.StickerRepository;
import com.threedollar.domain.stickeraction.StickerAction;
import com.threedollar.domain.stickeraction.StickerActionCountKey;
import com.threedollar.domain.stickeraction.repository.StickerActionCountRepository;
import com.threedollar.domain.stickeraction.repository.StickerActionRepository;
import com.threedollar.service.sticker.dto.request.AddStickerActionRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class StickerActionServiceTest extends IntegrationTest {

    @Autowired
    private StickerActionRepository stickerActionRepository;

    @Autowired
    private StickerActionService stickerActionService;

    @Autowired
    private StickerRepository stickerRepository;

    @Autowired
    private StickerActionCountRepository stickerActionCountRepository;

    @AfterEach
    void cleanUp() {
        stickerActionRepository.deleteAll();
        stickerRepository.deleteAll();
    }

    @Test
    void 스티커를_추가한다() {
        // given
        Sticker sticker = createSticker();
        AddStickerActionRequest request = getRequest(sticker);

        // when
        stickerActionService.upsertSticker(request, sticker.getStickerGroup(), request.getStickerIds());

        // then
        StickerAction stickerAction = getStickerAction(request, sticker.getStickerGroup());
        assertStickerAction(stickerAction, sticker.getStickerGroup(), stickerAction.getTargetId(), stickerAction.getAccountId(), stickerAction.getStickerIds());
    }

    @Test
    void 스티커를_제거한다() {
        // given
        Sticker sticker = createSticker();
        String accountId = "USER999L";
        String targetId = "1";
        StickerAction stickerAction = stickerActionRepository.save(StickerAction.newInstance(sticker.getStickerGroup(), Set.of(sticker.getId()), accountId, targetId));
        stickerActionCountRepository.incrBulkByCount(sticker.getStickerGroup(), targetId, Set.of(sticker.getId()));

        // when
        stickerActionService.deleteStickers(sticker.getStickerGroup(), stickerAction.getTargetId(), stickerAction.getAccountId());

        // then
        List<StickerAction> stickerActionList = stickerActionRepository.findAll();

        StickerActionCountKey key = StickerActionCountKey.of(sticker.getStickerGroup(), stickerAction.getTargetId(), sticker.getId());
        Map<StickerActionCountKey, Long> keyMap = stickerActionCountRepository.getStickerCountMap(List.of(key));
        assertThat(keyMap.get(key)).isEqualTo(0);

        assertThat(stickerActionList).isEmpty();
    }

    private void assertStickerAction(StickerAction stickerAction, StickerGroup stickerGroup, String targetId, String accountId, Set<Long> stickerIds) {
        assertThat(stickerAction.getStickerGroup()).isEqualTo(stickerGroup);
        assertThat(stickerAction.getTargetId()).isEqualTo(targetId);
        assertThat(stickerAction.getAccountId()).isEqualTo(accountId);
        assertThat(stickerAction.getStickerIds()).isEqualTo(stickerIds);
    }


    private StickerAction getStickerAction(AddStickerActionRequest request, StickerGroup stickerGroup) {
        return request.toEntity(stickerGroup);
    }

    private AddStickerActionRequest getRequest(Sticker sticker) {
        String targetId = "1L";
        String accountId = "USER_ACCOUNT999L";
        Set<Long> stickerIds = Set.of(sticker.getId());
        return AddStickerActionRequest.builder()
            .targetId(targetId)
            .stickerIds(stickerIds)
            .accountId(accountId)
            .build();
    }

    private Sticker createSticker() {
        String imageUrl = "imageUrl";
        StickerGroup stickerGroup = StickerGroup.COMMUNITY_COMMENT;
        return stickerRepository.save(Sticker.newInstance(imageUrl, stickerGroup));
    }

}
