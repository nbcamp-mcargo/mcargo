package com.mcargo.hubservice.infrastructure.jpa;

import com.mcargo.hubservice.domain.entity.HubRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HubRouteJpaRepository extends JpaRepository<HubRoute, UUID> {
}
