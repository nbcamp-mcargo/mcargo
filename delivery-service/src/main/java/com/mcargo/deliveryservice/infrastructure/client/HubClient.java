package com.mcargo.deliveryservice.infrastructure.client;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.deliveryservice.application.dto.HubRouteInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub-service")
public interface HubClient {

    // 서비스에서 호출해서 사용
    @GetMapping("/product/{id}") // 설정한 클라이언트에 있는 엔드포인트 호출
    ApiResponse<HubRouteInfo> getProduct(@PathVariable("id") String id);
}
