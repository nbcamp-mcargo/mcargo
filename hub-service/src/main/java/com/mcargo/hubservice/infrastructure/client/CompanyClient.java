package com.mcargo.hubservice.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "company-service")
public interface CompanyClient {


}
