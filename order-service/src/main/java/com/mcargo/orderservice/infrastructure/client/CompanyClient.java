package com.mcargo.orderservice.infrastructure.client;

import com.mcargo.orderservice.presentation.dto.ProductReadResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "company-service")
public interface CompanyClient {

    // 상품 아이디로 상품정보
    @GetMapping("/product/{productId}")
    ProductReadResponse getProduct(@PathVariable("productId") UUID productId);

}
