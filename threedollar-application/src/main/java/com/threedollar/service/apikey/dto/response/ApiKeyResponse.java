package com.threedollar.service.apikey.dto.response;

import com.threedollar.domain.apikey.ApiKey;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiKeyResponse {

    @NotNull
    private String apiKey;

    @NotNull
    private String workspaceId;

    @Nullable
    private String description;

    @Builder(access = AccessLevel.PRIVATE)
    public ApiKeyResponse(@NotNull String apiKey, @NotNull String workspaceId,
                          @Nullable String description) {
        this.apiKey = apiKey;
        this.workspaceId = workspaceId;
        this.description = description;
    }

    public static ApiKeyResponse from(@NotNull ApiKey apiKey) {
        return ApiKeyResponse.builder()
            .apiKey(apiKey.getApiKey())
            .workspaceId(apiKey.getWorkspaceId())
            .description(apiKey.getDescription())
            .build();
    }


}
