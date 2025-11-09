package com.mcargo.deliveryservice.presentation.controller;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.common.response.DeliveryResponseCode;
import com.mcargo.common.util.PageingUtils;
import com.mcargo.deliveryservice.application.dto.DeliverySearchParam;
import com.mcargo.deliveryservice.application.service.DeliveryService;
import com.mcargo.deliveryservice.domain.model.DeliveryStatusEnum;
import com.mcargo.deliveryservice.presentation.request.ReqDeliveryDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryDetailDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deliverys")
public class DeliveryController {
    private final DeliveryService deliveryService;

    // 배송 생성
    @PostMapping
    public ApiResponse<ResDeliveryDto> createDelivery(@RequestBody ReqDeliveryDto reqDeliveryDto){
        // TODO: 사용자 정보 추가

        ResDeliveryDto resDeliveryDto = deliveryService.createDelivery(reqDeliveryDto);

        return ApiResponse.of(DeliveryResponseCode.DELIVERY_INFO_CREATED, resDeliveryDto);
    }

    // 배송 취소
    @PostMapping("/{deliveryId}/cencel")
    public ApiResponse<ResDeliveryStatusDto> cancelDelivery(@PathVariable UUID deliveryId) {
        // TODO: 사용자 정보 추가

        ResDeliveryStatusDto resDeliveryStatusDto = deliveryService.updateDeliveryStatus(deliveryId, DeliveryStatusEnum.CANCELED);

        return ApiResponse.of(DeliveryResponseCode.DELIVERY_STATUS_UPDATE, resDeliveryStatusDto);
    }
    
    // 배송 목록 조회
    @GetMapping
    public ApiResponse<Page<ResDeliveryDetailDto>> getDeliveries(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,          // "createdAt" / "updatedAt"
            @RequestParam(defaultValue = "true") boolean isDescending
    ){
        // TODO: 권한별 서비스 메서드 분리 (유지보수성 향상)

        Pageable pageable = PageingUtils.createPageable(0, size, sortBy, isDescending);

        Page<ResDeliveryDetailDto> deliveryPage = deliveryService.getDeliveries(pageable);
        return ApiResponse.of(DeliveryResponseCode.DELIVERY_LIST_FETCHED, deliveryPage);
    }
    
    // 배송 단건 조회
    @GetMapping("/{deliveryId}")
    public ApiResponse<ResDeliveryDetailDto> getDelivery(@PathVariable UUID deliveryId){
        // TODO: 사용자 정보 추가

        ResDeliveryDetailDto delivery = deliveryService.getDeliveryDetail(deliveryId);

        return ApiResponse.of(DeliveryResponseCode.DELIVERY_DETAIL_FETCHED, delivery);
    }
    
    // 배송 검색
    // 주문번호, 수령인 이름, 배송 상태
    @GetMapping("/search")
    public ApiResponse<Page<ResDeliveryDetailDto>> searchDeliveries(
            @RequestParam(required = false) UUID orderId,
            @RequestParam(required = false) UUID receiverUserId,
            @RequestParam(required = false) DeliveryStatusEnum deliveryStatus,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,          // "createdAt" / "updatedAt"
            @RequestParam(defaultValue = "true") boolean isDescending
    ) {
        // TODO: 권한별 서비스 메서드 분리 (유지보수성 향상)

        Pageable pageable = PageingUtils.createPageable(0, size, sortBy, isDescending);

        DeliverySearchParam deliverySearchParam = new DeliverySearchParam(orderId, receiverUserId, deliveryStatus);

        Page<ResDeliveryDetailDto> deliverySearchPage = deliveryService.searchDeliveries(pageable, deliverySearchParam);
        return ApiResponse.of(DeliveryResponseCode.DELIVERY_SEARCHED, deliverySearchPage);
    }
}