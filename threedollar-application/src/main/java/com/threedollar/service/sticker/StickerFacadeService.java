package com.threedollar.service.sticker;

import com.threedollar.common.exception.NotFoundException;
import com.threedollar.domain.sticker.Sticker;
import com.threedollar.domain.sticker.StickerGroup;
import com.threedollar.service.sticker.dto.request.DeleteStickerAction;
import com.threedollar.service.sticker.dto.response.TargetStickerAction;
import com.threedollar.service.sticker.dto.request.AddStickerActionRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class StickerFacadeService {

    private final StickerActionService stickerActionService;

    private final StickerService stickerService;


    public void upsertSticker(AddStickerActionRequest request, @NotNull StickerGroup stickerGroup) {
        Set<Long> stickerIds = stickerService.getStickerListByStickerIdAndGroup(request.getStickerIds(), stickerGroup);
        if (stickerIds.size() != request.getStickerIds().size()) {
            throw new NotFoundException(String.format("(%s)에 해당하는 스티커는 사용할 수 없습니다.", request.getStickerIds()));
        }
        stickerActionService.upsertSticker(request, stickerGroup, stickerIds);
    }

    public void deleteSticker(DeleteStickerAction request, @NotNull StickerGroup stickerGroup) {
        stickerActionService.deleteStickers(stickerGroup, request.getTargetId(), request.getAccountId());
    }


    public List<TargetStickerAction> getTargetStickers(@NotNull StickerGroup stickerGroup, String accountId, Set<String> targetIds) {
        List<Sticker> stickers = stickerService.getStickersByStickerGroup(stickerGroup);
        return stickerActionService.getStickerActionResponse(stickerGroup, accountId, targetIds, stickers);
    }


}
