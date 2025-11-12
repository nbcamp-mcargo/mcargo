package com.mcargo.hubservice.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @GetMapping("/next-driver")
    UUID getNextDriver(@RequestParam(required = false) UUID hubId);

}
