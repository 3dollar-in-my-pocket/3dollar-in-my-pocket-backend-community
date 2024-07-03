package com.threedollar.service.poll;

import com.threedollar.common.exception.NotFoundException;
import com.threedollar.domain.poll.Poll;
import com.threedollar.domain.poll.repository.PollRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class PollServiceHelper {

    public static Poll getPollByIdAndAccountTypeAndWorkspaceId(PollRepository pollRepository, Long pollId, String accountId, String targetId, String workspaceId) {
        Poll poll = pollRepository.findByPollIdAndAccountIdAndTargetIdAndWorkspaceId(pollId, accountId, targetId, workspaceId);
        if(poll == null) {
            throw new NotFoundException(String.format("(%s)에 해당하는 투표는 존재하지 않습니다.", pollId));
        }
        return poll;
    }
}
