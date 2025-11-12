package com.mcargo.hubservice.infrastructure.Adapter;

import com.mcargo.hubservice.domain.entity.HubRoute;
import com.mcargo.hubservice.domain.repository.HubRouteRepository;
import com.mcargo.hubservice.infrastructure.jpa.HubRouteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HubRouteJpaRepositoryAdapter implements HubRouteRepository {

    private final HubRouteJpaRepository hubJpaRepository;

    @Override
    public void saveALl(List<HubRoute> allRoutes) {
        hubJpaRepository.saveAll(allRoutes);
    }

    @Override
    public void deleteAll() {
        hubJpaRepository.deleteAll();
    }

    @Override
    public HubRoute findByFromHubIdAndToHubId(UUID fromHubId, UUID toHubId) {
        return hubJpaRepository.findByFromHubIdAndToHubId(fromHubId, toHubId);
    }
}
