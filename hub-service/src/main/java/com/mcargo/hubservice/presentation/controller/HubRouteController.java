package com.mcargo.hubservice.presentation.controller;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.hubservice.domain.response.HubResponseCode;
import com.mcargo.hubservice.application.service.HubRouteService;
import com.mcargo.hubservice.presentation.dto.NavigateHubRouteRequest;
import com.mcargo.hubservice.presentation.dto.NavigateHubRouteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hub-routes")
@RequiredArgsConstructor
public class HubRouteController {

    private final HubRouteService hubRouteService;

    // 허브경로 생성
    @PostMapping
    public ApiResponse<Void> createHubRoute() {
        hubRouteService.createHubRoute();
        return ApiResponse.of(HubResponseCode.Hub_ROUTE_CREATED);
    }

    // 허브 경로 안내
    @PostMapping("/navigation")
    public ApiResponse<List<NavigateHubRouteResponse>> navigateHubRoute(
            @Valid @RequestBody NavigateHubRouteRequest request) {
        List<NavigateHubRouteResponse> response = hubRouteService.navigateHubRoute(request);
        return ApiResponse.of(HubResponseCode.HUB_OK, response);
    }


}
