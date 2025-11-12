package com.mcargo.deliveryservice.presentation.controller;

import com.mcargo.deliveryservice.domain.exception.DeliveryException;
import com.mcargo.common.response.ApiResponse;
import com.mcargo.deliveryservice.domain.response.DeliveryResponseCode;
import com.mcargo.common.util.PageingUtils;
import com.mcargo.deliveryservice.application.dto.DeliveryRouteSearchParam;
import com.mcargo.deliveryservice.application.service.DeliveryRouteService;
import com.mcargo.deliveryservice.domain.model.DeliveryRouteStatusEnum;
import com.mcargo.deliveryservice.presentation.request.ReqDeliveryRouteStatusDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryRouteDetailDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryRouteStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deliveries/routes")
public class DeliveryRouteController {
    private final DeliveryRouteService deliveryRouteService;

    // 배송 경로 상태 변경
    @PatchMapping("/{deliveryRouteId}/{deliveryRouteStatus}")
    public ApiResponse<ResDeliveryRouteStatusDto> updateDeliveryStatus(@PathVariable UUID deliveryRouteId,
                                                                       @PathVariable DeliveryRouteStatusEnum deliveryRouteStatus,
                                                                       @RequestBody ReqDeliveryRouteStatusDto reqDeliveryRouteStatusDto) {

        if(deliveryRouteStatus.equals(DeliveryRouteStatusEnum.COMPLETE) && reqDeliveryRouteStatusDto == null){
            new DeliveryException(DeliveryResponseCode.DELIVERY_REQUIRED_FIELD_MISSING);
        }

        ResDeliveryRouteStatusDto resDeliveryRouteStatusDto = deliveryRouteService.updateDeliveryStatus(deliveryRouteId, deliveryRouteStatus, reqDeliveryRouteStatusDto);

        return ApiResponse.of(DeliveryResponseCode.DELIVERY_STATUS_UPDATE, resDeliveryRouteStatusDto);
    }

    // 배송 경로 목록 조회
    @GetMapping
    public ApiResponse<Page<ResDeliveryRouteDetailDto>> getDeliveryRoutes(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,          // "createdAt" / "updatedAt"
            @RequestParam(defaultValue = "true") boolean isDescending){
        Pageable pageable = PageingUtils.createPageable(size, sortBy, isDescending);

        Page<ResDeliveryRouteDetailDto> deliveryRoutePage = deliveryRouteService.getDeliveryRoutes(pageable);
        return ApiResponse.of(DeliveryResponseCode.DELIVERY_LIST_FETCHED, deliveryRoutePage);
    }

    // 배송 경로 기록 검색
    // (검색 조건 : 배송 경로 ID, 배송 ID 출발허브ID, 도착허브ID, 현재상태)
    @GetMapping("/search")
    public ApiResponse<Page<ResDeliveryRouteDetailDto>> searchDeliveryRoutes(
            @RequestParam(required = false) UUID deliveryRouteId,
            @RequestParam(required = false) UUID deliveryId,
            @RequestParam(required = false) UUID fromHubId,
            @RequestParam(required = false) UUID toHubId,
            @RequestParam(required = false) DeliveryRouteStatusEnum deliveryRouteStatus,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,          // "createdAt" / "updatedAt"
            @RequestParam(defaultValue = "true") boolean isDescending
    ){
        Pageable pageable = PageingUtils.createPageable(size, sortBy, isDescending);
        DeliveryRouteSearchParam deliveryRouteSearchParam = new DeliveryRouteSearchParam(deliveryRouteId, deliveryId, fromHubId, toHubId, deliveryRouteStatus);

        Page<ResDeliveryRouteDetailDto> deliverySearchPage = deliveryRouteService.searchDeliveryRoutes(deliveryRouteSearchParam, pageable);

        return ApiResponse.of(DeliveryResponseCode.DELIVERY_SEARCHED, deliverySearchPage);
    }
}
