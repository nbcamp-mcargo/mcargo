package com.mcargo.slackservice.infrastructure.adapter;

import com.mcargo.slackservice.domain.entity.SlackMessage;
import com.mcargo.slackservice.domain.repository.SlackRepository;
import com.mcargo.slackservice.infrastructure.jparepository.SlackJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SlackJpaRepositoryAdapter implements SlackRepository {
    private final SlackJpaRepository slackJpaRepository;

    @Override
    public SlackMessage save(SlackMessage slackMessage) {
        return slackJpaRepository.save(slackMessage);
    }
}
