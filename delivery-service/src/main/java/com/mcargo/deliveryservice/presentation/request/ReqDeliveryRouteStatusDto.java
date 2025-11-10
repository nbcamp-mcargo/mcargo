package com.mcargo.deliveryservice.presentation.request;

import java.time.LocalDateTime;

public record ReqDeliveryRouteStatusDto(
        int actualDistance,
        LocalDateTime actualTime
) {}
