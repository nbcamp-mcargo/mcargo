package com.mcargo.deliveryservice.presentation.controller;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.common.response.DeliveryResponseCode;
import com.mcargo.common.util.PageingUtils;
import com.mcargo.deliveryservice.application.dto.DeliveryRouteSearchParam;
import com.mcargo.deliveryservice.application.dto.DeliverySearchParam;
import com.mcargo.deliveryservice.application.service.DeliveryRouteService;
import com.mcargo.deliveryservice.domain.model.DeliveryRouteStatusEnum;
import com.mcargo.deliveryservice.domain.model.DeliveryStatusEnum;
import com.mcargo.deliveryservice.presentation.request.ReqDeliveryRouteStatusDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryDetailDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryRouteDetailDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryRouteStatusDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deliverys/routes")
public class DeliveryRouteController {
    private final DeliveryRouteService deliveryRouteService;

    // 배송 경로 상태 변경
    @PatchMapping("/{deliveryRouteId}/{deliveryRouteStatus}")
    public ApiResponse<ResDeliveryRouteStatusDto> updateDeliveryStatus(@PathVariable UUID deliveryRouteId,
                                                                       @PathVariable DeliveryRouteStatusEnum deliveryRouteStatus,
                                                                       @RequestBody ReqDeliveryRouteStatusDto reqDeliveryRouteStatusDto) {
        ResDeliveryRouteStatusDto resDeliveryRouteStatusDto = deliveryRouteService.updateDeliveryStatus(deliveryRouteId, deliveryRouteStatus, reqDeliveryRouteStatusDto);

        return ApiResponse.of(DeliveryResponseCode.DELIVERY_STATUS_UPDATE, resDeliveryRouteStatusDto);
    }

    // 배송 경로 목록 조회
    @GetMapping
    public ApiResponse<Page<ResDeliveryRouteDetailDto>> getDeliveryRoutes(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,          // "createdAt" / "updatedAt"
            @RequestParam(defaultValue = "true") boolean isDescending){
        Pageable pageable = PageingUtils.createPageable(0, size, sortBy, isDescending);

        Page<ResDeliveryRouteDetailDto> deliveryRoutePage = deliveryRouteService.getDeliveryRoutes(pageable);
        return ApiResponse.of(DeliveryResponseCode.DELIVERY_LIST_FETCHED, deliveryRoutePage);
    }

    // 배송 경로 기록 검색
    // (검색 조건 : 배송 경로 ID, 출발허브ID, 도착허브ID, 현재상태)
    @GetMapping("/search")
    public ApiResponse<Page<ResDeliveryRouteDetailDto>> searchDeliveryRoutes(
            @RequestParam(required = false) UUID deliveryRouteId,
            @RequestParam(required = false) UUID fromHubId,
            @RequestParam(required = false) UUID toHubId,
            @RequestParam(required = false) DeliveryRouteStatusEnum deliveryRouteStatus,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,          // "createdAt" / "updatedAt"
            @RequestParam(defaultValue = "true") boolean isDescending
    ){
        Pageable pageable = PageingUtils.createPageable(0, size, sortBy, isDescending);

        DeliveryRouteSearchParam deliveryRouteSearchParam = new DeliveryRouteSearchParam(deliveryRouteId, fromHubId, toHubId, deliveryRouteStatus);

        Page<ResDeliveryRouteDetailDto> deliverySearchPage = deliveryRouteService.searchDeliveryRoutes(pageable, deliveryRouteSearchParam);
        return ApiResponse.of(DeliveryResponseCode.DELIVERY_SEARCHED, deliverySearchPage);
    }
}
