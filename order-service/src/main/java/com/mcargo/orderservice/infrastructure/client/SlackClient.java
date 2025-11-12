package com.mcargo.orderservice.infrastructure.client;

import com.mcargo.orderservice.presentation.dto.AiMessageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "slack-service")
public interface SlackClient {

    @PostMapping("/slack/send")
    Boolean sendMessage(@RequestBody AiMessageRequest request);
}
