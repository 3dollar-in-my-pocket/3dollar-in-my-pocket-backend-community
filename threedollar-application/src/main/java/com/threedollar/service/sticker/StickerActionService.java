package com.threedollar.service.sticker;


import com.threedollar.common.exception.NotFoundException;
import com.threedollar.domain.sticker.Sticker;
import com.threedollar.domain.sticker.StickerGroup;
import com.threedollar.domain.stickeraction.StickerAction;
import com.threedollar.domain.stickeraction.StickerActionCountKey;
import com.threedollar.domain.stickeraction.repository.StickerActionCountRepository;
import com.threedollar.domain.stickeraction.repository.StickerActionRepository;
import com.threedollar.service.sticker.dto.response.StickerWithActionResponse;
import com.threedollar.service.sticker.dto.response.TargetStickerResponse;
import com.threedollar.service.sticker.dto.request.AddStickerActionRequest;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StickerActionService {

    private final StickerActionRepository stickerActionRepository;

    private final StickerActionCountRepository stickerCountRepository;

    @Transactional
    public void upsertSticker(AddStickerActionRequest request, StickerGroup stickerGroup, Set<Long> stickerIds, String workspaceId) {
        StickerAction stickerAction = stickerActionRepository.getStickerActionByStickerGroupAndTargetIdAndAccountIdAndWorkspaceId(stickerGroup, request.getTargetId(), request.getAccountId(), workspaceId);

        if (stickerAction != null) {
            stickerCountRepository.decrBulkByCount(stickerGroup, workspaceId, stickerAction.getTargetId(), stickerAction.getStickerIds());
            stickerCountRepository.incrBulkByCount(stickerGroup, workspaceId, request.getTargetId(), stickerIds);
            stickerAction.update(stickerIds);
            return;
        }

        stickerActionRepository.save(StickerAction.newInstance(stickerGroup, workspaceId, stickerIds, request.getAccountId(), request.getTargetId()));
        stickerCountRepository.incrBulkByCount(stickerGroup, workspaceId, request.getTargetId(), stickerIds);
    }

    @Transactional
    public void deleteStickers(StickerGroup stickerGroup, String workspaceId, String targetId, String accountId) {
        StickerAction stickerAction = stickerActionRepository.getStickerActionByStickerGroupAndTargetIdAndAccountIdAndWorkspaceId(stickerGroup, targetId, accountId, workspaceId);

        if (stickerAction == null) {
            throw new NotFoundException(String.format("스티커 그룹 (%s)인 targetId (%s)에 해당하는 stickerAction 이 존재하지 않습니다.", stickerGroup, targetId));
        }
        stickerActionRepository.delete(stickerAction);
        stickerCountRepository.decrBulkByCount(stickerGroup, workspaceId, targetId, stickerAction.getStickerIds());

    }

    @Transactional(readOnly = true)
    public List<TargetStickerResponse> getStickerActionResponse(StickerGroup stickerGroup,
                                                                String workspaceId,
                                                                @Nullable String accountId,
                                                                Set<String> targetIds,
                                                                List<Sticker> stickers) {



        Map<String, StickerAction> targetIdActedByMe = getTargetIdActedByMe(targetIds, accountId, stickerGroup, workspaceId);

        return targetIds.stream()
            .map(targetId -> {
                List<StickerWithActionResponse> stickerWithActionResponses = stickers.stream()
                    .sorted(Comparator.comparingInt(Sticker::getPriority))
                    .map(
                        sticker -> {
                            long count = stickerCountRepository.getStickerCount(StickerActionCountKey.of(stickerGroup, workspaceId, targetId, sticker.getId()));
                            StickerAction stickerAction = targetIdActedByMe.getOrDefault(targetId, null);
                            return StickerWithActionResponse.of(sticker, count,
                                targetedByMe(stickerAction, sticker));
                        })
                    .toList();
                return TargetStickerResponse.builder()
                    .stickers(stickerWithActionResponses)
                    .targetId(targetId)
                    .build();
            })
            .collect(Collectors.toList());

    }

    private boolean targetedByMe(StickerAction stickerAction, Sticker sticker) {
        if (stickerAction == null || stickerAction.getStickerIds().isEmpty()) {
            return false;
        }
        return stickerAction.getStickerIds().contains(sticker.getId());
    }


    private Map<String, StickerAction> getTargetIdActedByMe(Set<String> targetIds,
                                                            @Nullable String accountId,
                                                            StickerGroup stickerGroup,
                                                            String workspaceId) {
        if (StringUtils.isBlank(accountId)) {
            return Collections.emptyMap();
        }

        List<StickerAction> stickerActions = stickerActionRepository.getStickerActionByMe(accountId, targetIds, stickerGroup, workspaceId);
        return stickerActions.stream()
            .collect(Collectors.toMap(StickerAction::getTargetId, stickerAction -> stickerAction));
    }

}
