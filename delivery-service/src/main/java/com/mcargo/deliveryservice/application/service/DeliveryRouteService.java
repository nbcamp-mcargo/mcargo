package com.mcargo.deliveryservice.application.service;

import com.mcargo.deliveryservice.application.dto.DeliveryRouteSearchParam;
import com.mcargo.deliveryservice.application.dto.HubRouteInfo;
import com.mcargo.deliveryservice.domain.model.Delivery;
import com.mcargo.deliveryservice.domain.model.DeliveryRoute;
import com.mcargo.deliveryservice.domain.model.DeliveryRouteStatusEnum;
import com.mcargo.deliveryservice.presentation.request.ReqDeliveryRouteStatusDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryRouteDetailDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryRouteStatusDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class DeliveryRouteService {

    public List<DeliveryRoute> createDeliveryRoute(UUID fromHubId, UUID toHubId){
        // TODO: 허브 API로 경로 정보 조회
//            List<HubRouteInfo> hubRoutes = hubClient.getRoutes(reqDeliveryDto.fromHubId(), reqDeliveryDto.toHubId());
        List<HubRouteInfo> hubRoutes = getMockHubRoutes(
                fromHubId,
                toHubId
        );

        List<DeliveryRoute> routes = new ArrayList<>();
        for(HubRouteInfo hubRouteInfo : hubRoutes){
            DeliveryRoute route = DeliveryRoute.createDeliveryRoute(
                    hubRouteInfo.sequence(),
                    hubRouteInfo.startHubId(),
                    hubRouteInfo.destHubId(),
                    hubRouteInfo.predictedDistance(),
                    hubRouteInfo.predictedTime(),
                    hubRouteInfo.deliveryDriverId()
            );

            routes.add(route);
        }

        return routes;
    }


    /**
     * Mock Hub 경로 정보 (Hub API 연동 전까지 사용)
     * TODO : Hub API 연동 후 제거
     */
    private List<HubRouteInfo> getMockHubRoutes(UUID fromHubId, UUID toHubId) {
        log.warn("Mock Hub 경로 정보 사용 중 - 실제 Hub API로 교체 필요");
        List<HubRouteInfo> routes = new ArrayList<>();

        // 중간에 거치는 허브
        UUID intermediateHubId = UUID.randomUUID();
        routes.add(new HubRouteInfo(
                fromHubId,
                intermediateHubId,
                60L,      // 1시간
                15000L, // 15km
                1,
                UUID.randomUUID()
        ));

        routes.add(new HubRouteInfo(
                intermediateHubId,
                toHubId,
                15L,      // 15분
                15000L, // 15km
                2,
                UUID.randomUUID()
        ));

        return routes;
    }

    public ResDeliveryRouteStatusDto updateDeliveryStatus(UUID deliveryRouteId, DeliveryRouteStatusEnum deliveryRouteStatus, ReqDeliveryRouteStatusDto reqDeliveryRouteStatusDto) {
        ResDeliveryRouteStatusDto resDeliveryRouteStatusDto = new ResDeliveryRouteStatusDto();
        return resDeliveryRouteStatusDto;
    }

    public Page<ResDeliveryRouteDetailDto> getDeliveryRoutes(Pageable pageable) {
        return null;
    }

    public Page<ResDeliveryRouteDetailDto> searchDeliveryRoutes(Pageable pageable, DeliveryRouteSearchParam deliveryRouteSearchParam) {
        return null;
    }
}
