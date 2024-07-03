package com.threedollar.service.sticker.dto.response;

import com.threedollar.domain.sticker.Sticker;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StickerResponse {

    private String groupName;

    private String imageUrl;

    private int priority;

    @Builder
    public StickerResponse(String groupName, String imageUrl, int priority) {
        this.groupName = groupName;
        this.imageUrl = imageUrl;
        this.priority = priority;
    }

    public static StickerResponse of(Sticker sticker) {
        return StickerResponse.builder()
                .groupName(sticker.getStickerGroup().name())
                .imageUrl(sticker.getImageUrl())
                .priority(sticker.getPriority())
                .build();
    }
}
