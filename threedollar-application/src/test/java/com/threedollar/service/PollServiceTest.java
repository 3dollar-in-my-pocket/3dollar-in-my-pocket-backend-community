package com.threedollar.service;

import com.threedollar.domain.AccountType;
import com.threedollar.domain.poll.Poll;
import com.threedollar.domain.poll.PollCategory;
import com.threedollar.domain.poll.repository.PollRepository;
import com.threedollar.service.poll.dto.request.PollCreateRequest;
import com.threedollar.service.poll.service.PollService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PollServiceTest {

    @Autowired
    private PollService pollService;

    @Autowired
    private PollRepository pollRepository;

    @AfterEach
    void clean_up() {
        pollRepository.deleteAll();
    }

    @Test
    void 투표를_추가합니다() {
        // given
        AccountType accountType = AccountType.USER_ACCOUNT;
        PollCategory pollCategory = PollCategory.BEST_FOOD;
        String accountId = "1L";
        String title = "제목";
        String content = "내용";
        LocalDateTime startDateTime = LocalDateTime.of(2024,1,2,19,0);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 1, 31, 18,59);

        PollCreateRequest request = PollCreateRequest.builder()
                .pollCategory(pollCategory)
                .startTime(startDateTime)
                .endTime(endDateTime)
                .title(title)
                .content(content)
                .options(Collections.emptyList())
                .build();

        // when
        pollService.addPoll(request, accountType, accountId);

        // then
        List<Poll> polls = pollRepository.findAll();
        assertThat(polls).hasSize(1);
        assertThat(polls.get(0).getPollCategory()).isEqualTo(pollCategory);
        assertThat(polls.get(0).getAccountId()).isEqualTo(accountId);
        assertThat(polls.get(0).getTitle()).isEqualTo(title);




    }
}
