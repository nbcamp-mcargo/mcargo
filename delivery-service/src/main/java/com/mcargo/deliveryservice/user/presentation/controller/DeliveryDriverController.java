package com.mcargo.deliveryservice.user.presentation.controller;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.common.response.ResponseCode;
import com.mcargo.deliveryservice.user.application.service.DeliveryDriverService;
import com.mcargo.deliveryservice.user.domain.model.DeliveryDriverType;
import com.mcargo.deliveryservice.user.domain.response.DriverResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * @param deliveryDriverType - 업체/허브 배송 구분
     * @return
     */
    @GetMapping("/next-driver")
    public ApiResponse<UUID> getNextDriver(@RequestParam UUID hubId) {
        UUID driver = deliveryDriverService.getNextDriver(hubId);

        return ApiResponse.of(DriverResponseCode.USER_GET_NEXT_DRIVER, driver);
    }
}
