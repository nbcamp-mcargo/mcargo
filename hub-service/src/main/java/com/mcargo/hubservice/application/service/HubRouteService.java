package com.mcargo.hubservice.application.service;

import com.mcargo.hubservice.application.dto.GetDistanceAndDurationResponse;
import com.mcargo.hubservice.application.dto.RouteCreatorsResponse;
import com.mcargo.hubservice.application.util.PredictDistanceAndDuration;
import com.mcargo.hubservice.application.util.RouteCreator;
import com.mcargo.hubservice.domain.entity.Hub;
import com.mcargo.hubservice.domain.entity.HubRoute;
import com.mcargo.hubservice.domain.repository.HubRouteRepository;
import com.mcargo.hubservice.presentation.dto.NavigateHubRouteRequest;
import com.mcargo.hubservice.presentation.dto.NavigateHubRouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HubRouteService {

    private final RouteCreator routeCreator;
    private final PredictDistanceAndDuration predictDistanceAndDuration;
    private final HubRouteRepository hubRouteRepository;
    private final HubService hubService;

    // 허브 경로 생성
    @Transactional
    public void createHubRoute() {
        List<Hub> findHubUds = hubService.getAllHub();
        List<RouteCreatorsResponse> responses = routeCreator.routeCreate(findHubUds);

        List<HubRoute> hubRoutes = new ArrayList<>();
        for (RouteCreatorsResponse route : responses) {
            // HubRoute 생성
            HubRoute hubRoute = HubRoute.create(route.fromHubId(), route.toHubId(), route.sequenceSteps());
            hubRoutes.add(hubRoute);
        }
        hubRouteRepository.deleteAll();
        hubRouteRepository.saveALl(hubRoutes);
    }

    // 허브 경로 안내.
    @Transactional(readOnly = true)
    public List<NavigateHubRouteResponse> navigateHubRoute(NavigateHubRouteRequest request) {
        List<NavigateHubRouteResponse> res = new ArrayList<>();
        int sequence = 1;
        // 출발 허브와 도착허브가 같지 않다면, 허브에서 바로 업체배송이 아닌 경우

        if (!request.fromHubId().equals(request.toHubId())) {
            int hubDriverNumber = 1; //TODO 허브배송담당자 배정
            HubRoute findHubRoute = hubRouteRepository.findByFromHubIdAndToHubId(request.fromHubId(), request.toHubId());
            sequence += findHubRoute.getHubRouteSequences().size(); // 마지막 업체배송 시퀀스를 구하기 위함
            findHubRoute.getHubRouteSequences().stream().forEach(
                seq -> {
                    Hub seqFromHub = hubService.getHubAsHub(seq.getSeqFromHubId());
                    Hub seqToHub = hubService.getHubAsHub(seq.getSeqToHubId());
                    GetDistanceAndDurationResponse hubPredictData = predictDistanceAndDuration.getDistanceAndDuration(seqFromHub, seqToHub);
                    res.add(NavigateHubRouteResponse.of(seq, seqFromHub, seqToHub, hubDriverNumber, hubPredictData));
                });
        }

        // 업체 배송
        Hub fromHub = hubService.getHubAsHub(request.toHubId());
        Hub toHub = hubService.getHubAsHub(request.fromHubId());
        GetDistanceAndDurationResponse companyPredictData = predictDistanceAndDuration.getDistanceAndDuration(fromHub, toHub);

        res.add(new NavigateHubRouteResponse(
            sequence,

            fromHub.getName(),
            toHub.getName(),        //TODO 컴퍼니로
            fromHub.getAddress(),
            toHub.getAddress(),     //TODO

            2, //TODO 업체 배송담당자 배정
            companyPredictData.distanceText(),
            companyPredictData.durationText()
        ));

        return res;
    }

}

