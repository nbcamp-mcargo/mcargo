package com.mcargo.slackservice.application.service;

import com.mcargo.slackservice.domain.exception.SlackException;
import com.mcargo.slackservice.domain.response.SlackResponseCode;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.conversations.ConversationsOpenResponse;
import com.slack.api.methods.response.users.UsersLookupByEmailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class SlackService {

    @Value("${slack.token}")
    private String slackToken;

    private final Slack slack = Slack.getInstance();

    // 이메일 → 유저ID → DM채널 → 메시지 보내기
    public void sendMessage(String email, String message) throws IOException, SlackApiException {
        // 이메일로 사용자 조회
        UsersLookupByEmailResponse userResponse = slack.methods(slackToken)
                .usersLookupByEmail(r -> r.email(email));

        if (!userResponse.isOk()) {
            log.info( userResponse.getError());
            throw new SlackException(SlackResponseCode.USER_NOT_FOUND);
        }

        String userId = userResponse.getUser().getId();
        log.info("유저 ID: {}", userId);

        // 해당 유저와 DM 채널 오픈
        ConversationsOpenResponse conversationResponse = slack.methods(slackToken)
                .conversationsOpen(r -> r.users(List.of(userId)));

        if (!conversationResponse.isOk()) {
            log.info(conversationResponse.getError());
            throw new SlackException(SlackResponseCode.SLACK_FAIL);
        }

        String channelId = conversationResponse.getChannel().getId();
        log.info("DM 채널 ID: {}", channelId);

        // 메시지 전송
        ChatPostMessageResponse messageResponse = slack.methods(slackToken)
                .chatPostMessage(r -> r
                        .channel(channelId)
                        .text(message)
                );

        if (messageResponse.isOk()) {
            log.info("메시지 전송 성공!");
        } else {
            log.info(messageResponse.getError());
            throw new SlackException(SlackResponseCode.SLACK_FAIL);
        }
    }

}