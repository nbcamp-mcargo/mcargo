package com.mcargo.hubservice.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "example-service") // feignClient로 사용할 클라이언트 이름(클라이언트에서 application에 지정한 name) 설정
public interface ExampleClient {

    // 정의 후 서비스에서 주입 후 사용
    @GetMapping("/product/{id}") // 설정한 클라이언트에 있는 엔드포인트 호출
    String getProduct(@PathVariable("id") String id);

}

