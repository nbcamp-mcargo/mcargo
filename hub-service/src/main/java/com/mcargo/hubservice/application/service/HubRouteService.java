package com.mcargo.hubservice.application.service;

import com.mcargo.hubservice.domain.entity.Hub;
import com.mcargo.hubservice.domain.entity.HubRoute;
import com.mcargo.hubservice.domain.repository.HubRouteRepository;
import com.mcargo.hubservice.infrastructure.kakaomap.KakaoMapApi;
import com.mcargo.hubservice.infrastructure.kakaomap.dto.GetDistanceAndDurationResponse;
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

    private final KakaoMapApi kakaoMapApi;
    private final HubRouteRepository hubRouteRepository;
    private final HubService hubService;

    // 허브 경로 생성
    @Transactional
    public void createHubRoute() {
        List<UUID> findHubUds = hubService.getAllHubId();
        //TODO 일단 동작은 하도록 p2p로 구현
        List<HubRoute> allRoutes = new ArrayList<>();
        for (int i = 0; i < findHubUds.size(); i++) {
            for (int j = 0; j < findHubUds.size(); j++) {
                allRoutes.add(
                        HubRoute.create(findHubUds.get(i), findHubUds.get(j)));
            }
        }
        hubRouteRepository.saveALl(allRoutes);
    }

    // 허브 경로 안내. p2p라 경로 저장할 필요가 없어서 db 조회는 없는 상태
    // @Transactional(readOnly = true)
    public List<NavigateHubRouteResponse> navigateHubRoute(NavigateHubRouteRequest request) {
        List<NavigateHubRouteResponse> res = new ArrayList<>();
        Hub fromHub = hubService.getHub2(request.fromHubId());
        Hub toHub = hubService.getHub2(request.toHubId());
        //TODO 컴퍼니 정보 요청

        // 카카오api 요청
        GetDistanceAndDurationResponse expectData = kakaoMapApi.getDistanceAndDuration(fromHub, toHub);

        NavigateHubRouteResponse n1 = new NavigateHubRouteResponse(
                1,

                fromHub.getName(),
                toHub.getName(),
                fromHub.getAddress(),
                toHub.getAddress(),

                null, // 배송 경로 기록에서 배정
                expectData.distanceText(),
                expectData.durationText()
        );
        NavigateHubRouteResponse n2 = new NavigateHubRouteResponse(
                2,

                toHub.getName(),
                "수령업체", //TODO 수령업체 관련 수정예정
                toHub.getAddress(),
                "수령업체 주소",

                1, //hubService.assignDriver(request.toHubId()), TODO 배송담당자 요청 구현하고 수정
                "3",
                "4"
        );

        res.add(n1);
        res.add(n2);

        return res;
    }


}

