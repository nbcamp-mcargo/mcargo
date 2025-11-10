package com.mcargo.hubservice.application.service;

import com.mcargo.hubservice.domain.entity.HubRoute;
import com.mcargo.hubservice.domain.repository.HubRouteRepository;
import com.mcargo.hubservice.presentation.dto.NavigateHubRouteRequest;
import com.mcargo.hubservice.presentation.dto.NavigateHubRouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubRouteService {

    private final HubRouteRepository hubRouteRepository;
    private final HubService hubService;

    @Transactional
    public void createHubRoute() {
        List<UUID> findHubUds = hubService.getAllHubId();
        //TODO 일단 만들자
        List<HubRoute> allRoutes = new ArrayList<>();
        // 모든 조합 생성 (from ≠ to)
        for (int i = 0; i < findHubUds.size(); i++) {
            for (int j = 0; j < findHubUds.size(); j++) {
                if (i == j) continue; // 출발지와 도착지는 같을 수 없음

                allRoutes.add(
                        HubRoute.create(findHubUds.get(i), findHubUds.get(j)));
            }
        }
        hubRouteRepository.saveALl(allRoutes);
    }

    public List<NavigateHubRouteResponse> navigateHubRoute(NavigateHubRouteRequest request) {
        List<NavigateHubRouteResponse> res = new ArrayList<>();
        String q1 = hubService.getHubAddress(request.fromHubId());
        String q2 = hubService.getHubAddress(request.toHubId());


        NavigateHubRouteResponse n1 = new NavigateHubRouteResponse(
                1,
                q1,
                q2,
                UUID.randomUUID(), //TODO api로 예상 거리, 시간 추가 예정
                1,
                2
        );
        NavigateHubRouteResponse n2 = new NavigateHubRouteResponse(
                2,
                q2,
                request.destinationAddress(),
                UUID.randomUUID(),
                3,
                4
        );

        res.add(n1);
        res.add(n2);

        return res;
    }
}

