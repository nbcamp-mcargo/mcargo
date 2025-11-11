package com.mcargo.slackservice.presentation;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.slackservice.application.service.SlackService;
import com.mcargo.slackservice.domain.response.SlackResponseCode;
import com.mcargo.slackservice.presentation.dto.AiMessageRequest;
import com.slack.api.methods.SlackApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class SlackController {

    private final SlackService slackService;

    @PostMapping("/slack/send")
    public ApiResponse<Void> sendMessage(
            @RequestBody AiMessageRequest request) throws SlackApiException, IOException {

        slackService.sendMessage(request.userEmail(), request.message());
        return ApiResponse.of(SlackResponseCode.SLACK_OK);
    }

}
