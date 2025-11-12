package com.mcargo.orderservice.infrastructure.client;

import com.mcargo.orderservice.presentation.dto.CreateOrderProductRequest;
import com.mcargo.orderservice.presentation.dto.GetHubProductOrderableResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "hub-service")
public interface HubClient {

    @PostMapping("/hubs/products/orderable")
    List<GetHubProductOrderableResponse> getHubProductOrderable(
            @RequestBody List<CreateOrderProductRequest> request);

}
