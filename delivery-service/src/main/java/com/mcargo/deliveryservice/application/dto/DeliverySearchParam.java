package com.mcargo.deliveryservice.application.dto;

import com.mcargo.deliveryservice.domain.model.DeliveryStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


public record DeliverySearchParam(
        UUID orderId,
        UUID receiverUserId,
        DeliveryStatusEnum deliveryStatus) {}
