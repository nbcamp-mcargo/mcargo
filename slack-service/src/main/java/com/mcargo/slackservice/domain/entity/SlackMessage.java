package com.mcargo.slackservice.domain.entity;

import com.mcargo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_slack_message")
@Where(clause = "deleted_at IS NULL")
public class SlackMessage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "slack_message_id", columnDefinition = "uuid")
    private UUID id;

    private String recipient; // 수신자 이메일

    @Enumerated(EnumType.STRING)
    private MessageState messageState; // 송신 성공, 실패 여부

    @Column(columnDefinition = "TEXT")
    private String aiRequestMessage; // ai에 요청할 메시지

    @Column(columnDefinition = "TEXT")
    private String aiResponseMessage; // ai응답을 저장할 메시지

    // 메시지 엔티티 생성
    public static SlackMessage create(String recipient, String aiRequestMessage, String aiResponseMessage) {
        SlackMessage slackMessage = new SlackMessage();
        slackMessage.recipient = recipient;
        slackMessage.aiRequestMessage = aiRequestMessage;
        slackMessage.aiResponseMessage = aiResponseMessage;
        slackMessage.messageState = MessageState.FAILED;
        return slackMessage;
    }

    // 송신 성공 시 상태 변경
    public void success() {
        this.messageState = MessageState.SUCCESS;
    }

}
