package com.threedollar.service.sticker.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class TargetStickerResponse {

    private String targetId;

    private List<StickerWithActionResponse> stickers;

    @Builder
    public TargetStickerResponse(String targetId, List<StickerWithActionResponse> stickers) {
        this.targetId = targetId;
        this.stickers = stickers;
    }

}
