package com.mcargo.slackservice.domain.repository;

import com.mcargo.slackservice.domain.entity.SlackMessage;

public interface SlackRepository {
    SlackMessage save(SlackMessage slackMessage);
}
