package com.threedollar.threedollardomain.domain.poll.repository;

import com.threedollar.threedollardomain.domain.poll.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {
}
