package com.mcargo.hubservice.infrastructure.Adapter;

import com.mcargo.hubservice.domain.entity.HubRoute;
import com.mcargo.hubservice.domain.repository.HubRouteRepository;
import com.mcargo.hubservice.infrastructure.jpa.HubRouteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HubRouteJpaRepositoryAdapter implements HubRouteRepository {

    private final HubRouteJpaRepository hubJpaRepository;


    @Override
    public void saveALl(List<HubRoute> allRoutes) {
        hubJpaRepository.saveAll(allRoutes);
    }
}
