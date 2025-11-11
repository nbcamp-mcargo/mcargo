package com.mcargo.slackservice.presentation;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.slackservice.application.service.SlackService;
import com.mcargo.slackservice.domain.response.SlackResponseCode;
import com.slack.api.methods.SlackApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class SlackController {

    private final SlackService slackService;

    @PostMapping("/slack/send")
    public ApiResponse<Void> sendMessage(
            @RequestParam String userEmail,
            @RequestParam String text) throws SlackApiException, IOException {

        slackService.sendMessage(userEmail, text);
        return ApiResponse.of(SlackResponseCode.SLACK_OK);
    }

}
