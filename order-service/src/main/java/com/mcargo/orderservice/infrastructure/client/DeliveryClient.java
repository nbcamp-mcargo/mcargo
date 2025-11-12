package com.mcargo.orderservice.infrastructure.client;

import com.mcargo.orderservice.presentation.dto.ReqDeliveryDto;
import com.mcargo.orderservice.presentation.dto.ResDeliveryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "delivery-service")
public interface DeliveryClient {

    // 배송으로 배송 정보 작성 요청
    @PostMapping("/deliveries")
    ResDeliveryDto createDelivery(@RequestBody ReqDeliveryDto reqDeliveryDto);

//    // 메세지 요청
//    @PostMapping("")
//    MessageCreateResponse createMessage(@RequestBody MessageCreateRequest messageCreateRequest);
}
