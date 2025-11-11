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

    private String message;

    public static SlackMessage create(String recipient, String message) {
        SlackMessage slackMessage = new SlackMessage();
        slackMessage.recipient = recipient;
        slackMessage.message = message;
        slackMessage.messageState = MessageState.FAILED;
        return slackMessage;
    }

    public void success() {
        this.messageState = MessageState.SUCCESS;
    }

}
