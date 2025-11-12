package com.mcargo.slackservice.infrastructure.jparepository;

import com.mcargo.slackservice.domain.entity.SlackMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SlackJpaRepository extends JpaRepository<SlackMessage, UUID> {
}
