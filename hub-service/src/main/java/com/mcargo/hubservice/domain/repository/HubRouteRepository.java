package com.mcargo.hubservice.domain.repository;

import com.mcargo.hubservice.domain.entity.HubRoute;

import java.util.List;
import java.util.UUID;

public interface HubRouteRepository {
    void saveALl(List<HubRoute> allRoutes);

    void deleteAll();

    HubRoute findByFromHubIdAndToHubId(UUID fromHubId, UUID toHubId);
}
