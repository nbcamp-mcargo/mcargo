package com.mcargo.hubservice.infrastructure.routecreator;

import com.mcargo.hubservice.application.dto.RouteCreatorsResponse;
import com.mcargo.hubservice.application.util.RouteCreator;
import com.mcargo.hubservice.domain.entity.Hub;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RouteCreators implements RouteCreator {

    // P2P
    @Override
    public List<RouteCreatorsResponse> routeCreate(List<Hub> hubs) {
        List<RouteCreatorsResponse> responses = new ArrayList<>();

        // 모든 허브를 출발지-도착지 쌍으로 반복
        for (int i = 0; i < hubs.size(); i++) {
            for (int j = 0; j < hubs.size(); j++) {
                if (i == j) continue; // 출발지 == 도착지 제외

                Hub fromHub = hubs.get(i);
                Hub toHub = hubs.get(j);
                int sequenceCounter = 1;

                // 여기서 sequenceSteps를 생성
                List<RouteCreatorsResponse.SequenceStep> sequenceSteps = new ArrayList<>();

                // P2P니까 현재는 단일 경로지만, 나중에 경유지를 추가하면 sequenceSteps에 여러 개를 넣을 수 있음
                sequenceSteps.add(new RouteCreatorsResponse.SequenceStep(
                        sequenceCounter++,  // 순번
                        fromHub.getId(),    // 시퀀스 출발 허브
                        toHub.getId()       // 시퀀스 도착 허브
                ));

                // RouteCreatorsResponse 생성
                RouteCreatorsResponse route = new RouteCreatorsResponse(
                        fromHub.getId(),
                        toHub.getId(),
                        sequenceSteps
                );

                responses.add(route);
            }
        }

        return responses;
    }

}



//        List<HubRoute> allRoutes = new ArrayList<>();
//        for (int i = 0; i < hubs.size(); i++) {
//            for (int j = 0; j < hubs.size(); j++) {
//                if (!hubs.get(i).equals(hubs.get(j))) { // 출발지 ≠ 도착지
//                    allRoutes.add(
//                            HubRoute.create(hubs.get(i), hubs.get(j))
//                    );
//                }
//            }
//        }
//        hubRouteRepository.saveALl(allRoutes);