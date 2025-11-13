package com.mcargo.authservice.presentation.controller;

import com.mcargo.authservice.application.service.DeliveryDriverService;
import com.mcargo.authservice.domain.response.DriverResponseCode;
import com.mcargo.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/drivers")
public class DeliveryDriverController {
    private final DeliveryDriverService deliveryDriverService;

    /**
     * 배송 담당자 요청
     *
     * @param hubId - 업체 배송인 경우
     * @return
     */
    @GetMapping("/next-driver")
    public ApiResponse<UUID> getNextDriver(@RequestParam UUID hubId) {
        UUID driver = deliveryDriverService.getNextDriver(hubId);

        return ApiResponse.of(DriverResponseCode.USER_GET_NEXT_DRIVER, driver);
    }

    @PostMapping("/new-driver-seq")
    public ApiResponse<UUID> createDriverSeq(@RequestParam UUID hubId) {
        deliveryDriverService.createDeliveryDriverSeq(hubId);

        return ApiResponse.of(DriverResponseCode.CREATE_DEIVER_SEQ, null);
    }
}
