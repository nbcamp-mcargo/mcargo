package com.mcargo.deliveryservice.application.service;

import com.mcargo.common.exception.DeliveryException;
import com.mcargo.deliveryservice.application.dto.DeliverySearchParam;
import com.mcargo.deliveryservice.domain.exception.DeliveryErrorCode;
import com.mcargo.deliveryservice.domain.model.Delivery;
import com.mcargo.deliveryservice.domain.model.DeliveryRoute;
import com.mcargo.deliveryservice.domain.model.DeliveryStatusEnum;
import com.mcargo.deliveryservice.domain.repository.DeliveryRepository;
import com.mcargo.deliveryservice.presentation.request.ReqDeliveryDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryDetailDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryRouteDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryStatusDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryRouteService deliveryRouteService;

//    private final HubClient hubClient;


    /**
     * 배송 정보 생성 (배송 + 배송 경로)
     */
    @Transactional
    public ResDeliveryDto createDelivery(ReqDeliveryDto reqDeliveryDto){
        log.info("배송 생성 시작 - orderId: {}, fromHubId: {}, toHubId: {}",
                reqDeliveryDto.orderId(),
                reqDeliveryDto.fromHubId(),
                reqDeliveryDto.toHubId());

        if(deliveryRepository.existsByOrderIdAndDeletedAtIsNull(reqDeliveryDto.orderId())) {
            throw new DeliveryException(DeliveryErrorCode.DELIVERY_ALREADY_EXISTS);
        }

        // 배송 정보 생성
        Delivery delivery = Delivery.createDelivery(reqDeliveryDto.orderId(),
                reqDeliveryDto.address(),
                reqDeliveryDto.receiverUserId(),
                reqDeliveryDto.receiverSlackId());

        List<DeliveryRoute> routes = deliveryRouteService.createDeliveryRoute(reqDeliveryDto.fromHubId(), reqDeliveryDto.toHubId());

        // cascade로 DeliveryRoute도 함께 저장될 수 있도록
        for(DeliveryRoute route : routes){
            delivery.addRoute(route);
        }

        Delivery savedDelivery = deliveryRepository.save(delivery);

        log.info("배송 생성 완료 - deliveryId: {}, 경로 수: {}",
                savedDelivery.getDeliveryId(),
                savedDelivery.getDeliveryRoutes().size());

        List<ResDeliveryRouteDto> routeDtos = delivery.getDeliveryRoutes().stream()
                .filter(route -> route.getDeletedAt() == null)
                .map(route -> new ResDeliveryRouteDto(
                        route.getDeliveryRouteId(),
                        route.getSequence(),
                        route.getFromHubId(),
                        route.getToHubId(),
                        route.getDeliveryRouteStatus(),
                        route.getPredictedDistance(),
                        route.getPredictedTime(),
                        route.getDeliveryDriverId(),
                        route.getCreatedAt(),
                        route.getCreatedBy()
                ))
                .collect(Collectors.toList());

        ResDeliveryDto resDeliveryDto = new ResDeliveryDto(
                delivery.getDeliveryId(),
                delivery.getOrderId(),
                delivery.getDeliveryStatus(),
                delivery.getAddress(),
                delivery.getReceiverUserId(),
                delivery.getReceiverSlackId(),
                delivery.getCreatedAt(),
                delivery.getCreatedBy(),
                routeDtos
        );

        return resDeliveryDto;
    }

    @Transactional
    public void cancelDelivery(UUID deliveryId) {
        Delivery delivery = findById(deliveryId);

        if(delivery.getDeliveryStatus().equals(DeliveryStatusEnum.CANCELED)){
            new DeliveryException(DeliveryErrorCode.DELIVERY_ALREADY_CANCELED);
        }

        delivery.cencelDeliveryWithRoutes();
    }

    @Transactional
    public void deleteDelivery(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DeliveryException(DeliveryErrorCode.DELIVERY_NOT_FOUND));

        delivery.softDeleteWithRoutes();
    }

    public Page<ResDeliveryDetailDto> getDeliveries(Pageable pageable) {
        Page<Delivery> deliveryPage = deliveryRepository.findAllByDeletedAtIsNull(pageable);

        return deliveryPage.map(this::toResDeliveryDetailDto);
    }

    public ResDeliveryDetailDto getDeliveryDetail(UUID deliveryId) {
        Delivery delivery = findById(deliveryId);

        return toResDeliveryDetailDto(delivery);
    }

    public Page<ResDeliveryDetailDto> searchDeliveries(DeliverySearchParam deliverySearchParam, Pageable pageable) {
        Page<Delivery> deliveryPage = deliveryRepository.searchDeliveries(deliverySearchParam, pageable);

        return deliveryPage.map(this::toResDeliveryDetailDto);
    }

    public ResDeliveryStatusDto updateDeliveryStatus(UUID deliveryId, DeliveryStatusEnum deliveryStatus) {

        ResDeliveryStatusDto resDeliveryStatusDto = ResDeliveryStatusDto.builder()
                .deliveryId(deliveryId)
                .deliveryStatus(deliveryStatus)
                .build();

        return resDeliveryStatusDto;
    }

    private ResDeliveryDetailDto toResDeliveryDetailDto(Delivery delivery) {
        return new  ResDeliveryDetailDto(
                delivery.getDeliveryId(),
                delivery.getOrderId(),
                delivery.getDeliveryStatus(),
                delivery.getAddress(),
                delivery.getReceiverUserId(),
                delivery.getReceiverSlackId(),
                delivery.getCreatedAt(),
                delivery.getCreatedBy(),
                delivery.getUpdatedAt(),
                delivery.getUpdatedBy()
        );
    }

    public Delivery findById(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DeliveryException(DeliveryErrorCode.DELIVERY_NOT_FOUND));
    }
}
