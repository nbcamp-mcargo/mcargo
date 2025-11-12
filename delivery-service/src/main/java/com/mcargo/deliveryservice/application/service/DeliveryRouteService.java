package com.mcargo.deliveryservice.application.service;

import com.mcargo.common.exception.DeliveryException;
import com.mcargo.deliveryservice.application.dto.DeliveryRouteSearchParam;
import com.mcargo.deliveryservice.application.dto.HubRouteInfo;
import com.mcargo.deliveryservice.domain.exception.DeliveryErrorCode;
import com.mcargo.deliveryservice.domain.model.DeliveryRoute;
import com.mcargo.deliveryservice.domain.model.DeliveryRouteStatusEnum;
import com.mcargo.deliveryservice.domain.repository.DeliveryRouteReposotory;
import com.mcargo.deliveryservice.presentation.request.ReqDeliveryRouteStatusDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryRouteDetailDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryRouteStatusDto;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryRouteService {

    private final DeliveryRouteReposotory deliveryRouteReposotory;

    public List<DeliveryRoute> createDeliveryRoute(UUID fromHubId, UUID toHubId, String address){
        // TODO: 허브 API로 경로 정보 조회
//            List<HubRouteInfo> hubRoutes = hubClient.getRoutes(reqDeliveryDto.fromHubId(), reqDeliveryDto.toHubId());
        List<HubRouteInfo> hubRoutes = getMockHubRoutes(
                fromHubId,
                toHubId
        );

        List<DeliveryRoute> routes = new ArrayList<>();
        for(HubRouteInfo hubRouteInfo : hubRoutes){
            UUID deliveryDriveId = hubRouteInfo.deliveryDriverId();
            if(deliveryDriveId == null) {
                deliveryDriveId = addDeliveryDriver();
            }

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

    private UUID addDeliveryDriver() {
        // TODO : 배송 기사 추가

        return null;
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

    @Transactional
    public ResDeliveryRouteStatusDto updateDeliveryStatus(UUID deliveryRouteId, DeliveryRouteStatusEnum deliveryRouteStatus, ReqDeliveryRouteStatusDto reqDeliveryRouteStatusDto) {
        DeliveryRoute deliveryRoute = findById(deliveryRouteId);

        if(deliveryRoute.getDeliveryRouteStatus().equals(DeliveryRouteStatusEnum.CANCELED)){
            new DeliveryException(DeliveryErrorCode.DELIVERY_ALREADY_CANCELED);
        }

        deliveryRoute.updateStatus(deliveryRouteStatus);

        ResDeliveryRouteStatusDto resDeliveryRouteStatusDto = new ResDeliveryRouteStatusDto(
                deliveryRoute.getDeliveryRouteId(),
                deliveryRoute.getDeliveryRouteStatus()
        );

        return resDeliveryRouteStatusDto;
    }

    public Page<ResDeliveryRouteDetailDto> getDeliveryRoutes(Pageable pageable) {
        Page<DeliveryRoute> deliveryRoutePage = deliveryRouteReposotory.findAllByDeletedAtIsNull(pageable);

        return deliveryRoutePage.map(this::toResDeliveryRouteDetailDto);
    }

    private ResDeliveryRouteDetailDto toResDeliveryRouteDetailDto(DeliveryRoute deliveryRoute) {
        return new ResDeliveryRouteDetailDto(
                deliveryRoute.getDeliveryRouteId(),
                deliveryRoute.getDeliveryId(),
                deliveryRoute.getSequence(),
                deliveryRoute.getFromHubId(),
                deliveryRoute.getToHubId(),
                deliveryRoute.getPredictedDistance(),
                deliveryRoute.getPredictedTime(),
                deliveryRoute.getActualDistance(),
                deliveryRoute.getActualTime(),
                deliveryRoute.getDeliveryDriverId(),
                deliveryRoute.getDeliveryRouteStatus(),
                deliveryRoute.getCreatedAt(),
                deliveryRoute.getCreatedBy(),
                deliveryRoute.getUpdatedAt(),
                deliveryRoute.getUpdatedBy()
        );
    }

    public Page<ResDeliveryRouteDetailDto> searchDeliveryRoutes(DeliveryRouteSearchParam deliveryRouteSearchParam, Pageable pageable) {
        Page<DeliveryRoute> deliveryRoutePage = deliveryRouteReposotory.searchDeliveries(deliveryRouteSearchParam, pageable);

        return deliveryRoutePage.map(this::toResDeliveryRouteDetailDto);
    }

    public DeliveryRoute findById(UUID deliveryRouteId) {
        return deliveryRouteReposotory.findById(deliveryRouteId)
                .orElseThrow(() -> new DeliveryException(DeliveryErrorCode.DELEVERY_ROUTE_NOT_FOUND));
    }
}
