package com.threedollar.service.poll.dto.response;

import com.threedollar.domain.options.PollOption;
import com.threedollar.domain.poll.Poll;
import com.threedollar.service.common.dto.response.CursorResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class AllPollResponse {

    private String title;

    private String content;

    private String accountId;

    private List<OptionsResponse> optionsResponses;

    private LocalDateTime endTime;

    private CursorResponse cursorResponse;

    @Builder
    public AllPollResponse(String title, String content, String accountId, List<OptionsResponse> optionsResponses, LocalDateTime endTime,
                           CursorResponse cursorResponse) {
        this.title = title;
        this.content = content;
        this.accountId = accountId;
        this.optionsResponses = optionsResponses;
        this.endTime = endTime;
        this.cursorResponse = cursorResponse;
    }

    public static AllPollResponse of(Poll poll) {
        return AllPollResponse.builder()
                .title(poll.getTitle())
                .content(poll.getContent())
                .accountId(poll.getAccountId())
                .optionsResponses(getOptionResponse(poll.getOptions()))
                .endTime(poll.getEndDateTime())
                .build();
    }

    public static List<OptionsResponse> getOptionResponse(List<PollOption> options) {
        return options.stream()
                .map(OptionsResponse::of)
                .collect(Collectors.toList());
    }

}
