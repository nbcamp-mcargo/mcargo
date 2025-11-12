package com.mcargo.slackservice.application.service;

import com.mcargo.slackservice.application.service.util.PredictAiUtils;
import com.mcargo.slackservice.domain.entity.SlackMessage;
import com.mcargo.slackservice.domain.exception.SlackException;
import com.mcargo.slackservice.domain.repository.SlackRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class SlackService {

    @Value("${slack.token}")
    private String slackToken;

    private final SlackRepository slackRepository;
    private final PredictAiUtils predictAiUtils;

    private final Slack slack = Slack.getInstance();

    // AI 요청 -> 슬랙 메시지 송신
    // 슬랙 프로세스 : 이메일 -> 유저ID -> DM채널 -> 메시지 송신
    @Transactional
    public Boolean sendMessage(String email, String aiRequestMessage) throws IOException, SlackApiException {

        // AI 요청
        String aiResponseMessage = predictAiUtils.predictAi(aiRequestMessage);

        // 이메일로 사용자 조회
        UsersLookupByEmailResponse userResponse = slack.methods(slackToken)
                .usersLookupByEmail(r -> r.email(email));

        if (!userResponse.isOk()) {
            log.info( userResponse.getError());
            throw new SlackException(SlackResponseCode.USER_NOT_FOUND);
        }

        // 유저 찾기. 찾으면 메시지 저장
        String userId = userResponse.getUser().getId();
        log.info("유저 ID: {}", userId);
        SlackMessage slackMessage = SlackMessage.create(email, aiRequestMessage, aiResponseMessage);
        slackRepository.save(slackMessage); // 영속성 컨텍스트에 담김

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
                        .text(aiResponseMessage)
                );

        if (messageResponse.isOk()) {
            slackMessage.success();
            log.info("메시지 전송 성공!");
        } else {
            log.info(messageResponse.getError());
            throw new SlackException(SlackResponseCode.SLACK_FAIL);
        }
        return true;
    }

}