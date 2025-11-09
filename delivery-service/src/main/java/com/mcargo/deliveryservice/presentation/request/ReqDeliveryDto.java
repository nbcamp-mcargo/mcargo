package com.mcargo.deliveryservice.presentation.request;

import java.util.UUID;

public record ReqDeliveryDto(
        UUID orderId,
        UUID fromHubId,
        UUID toHubId,
        String address,
        Long receiverUserId,
        String receiverSlackId
) {}