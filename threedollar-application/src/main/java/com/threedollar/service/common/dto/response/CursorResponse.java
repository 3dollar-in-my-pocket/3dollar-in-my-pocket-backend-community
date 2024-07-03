package com.threedollar.service.common.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CursorResponse {

    private boolean hasNext;

    private Long nextCursor;

    @Builder
    public CursorResponse(boolean hasNext, Long nextCursor) {
        this.hasNext = hasNext;
        this.nextCursor = nextCursor;
    }

}
