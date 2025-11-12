package com.mcargo.deliveryservice.infrastructure.client;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.deliveryservice.application.dto.HubRouteInfo;
import com.mcargo.deliveryservice.application.dto.NavigateHubRouteRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "hub-service")
public interface HubClient {

    @PostMapping("/hub-routes/navigation")
    List<HubRouteInfo> getHubRoutes(@RequestBody NavigateHubRouteRequest request);
}
