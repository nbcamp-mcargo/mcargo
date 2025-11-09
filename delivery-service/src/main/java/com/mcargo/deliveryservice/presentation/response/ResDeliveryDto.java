package com.mcargo.deliveryservice.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResDeliveryDto {
    private UUID orderId;
    private UUID fromHubId;
    private UUID toHubId;
    private String address;
    private Long receiverUserId;
    private String receiverSlackId;
    private LocalDateTime createdAt;
    private Long createdBy;
}