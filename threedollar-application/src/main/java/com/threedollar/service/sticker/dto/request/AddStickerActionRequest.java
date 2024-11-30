package com.threedollar.service.sticker.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Getter
public class AddStickerActionRequest {

    @NotBlank
    private String targetId;

    @Nullable
    private String accountId;

    @NotEmpty
    private Set<@NotBlank @Size(max = 100) String> stickerNames;

    @Builder
    public AddStickerActionRequest(String targetId, @Nullable String accountId, Set<String> stickerNames) {
        this.targetId = targetId;
        this.accountId = accountId;
        this.stickerNames = stickerNames;
    }

}
