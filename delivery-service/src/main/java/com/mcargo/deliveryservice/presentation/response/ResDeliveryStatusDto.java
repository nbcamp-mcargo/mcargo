package com.mcargo.deliveryservice.presentation.response;

import com.mcargo.deliveryservice.domain.model.DeliveryStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResDeliveryStatusDto {
    private UUID deliveryId;
    private DeliveryStatusEnum deliveryStatus;
}
