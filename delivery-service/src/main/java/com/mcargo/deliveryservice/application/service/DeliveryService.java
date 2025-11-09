package com.mcargo.deliveryservice.application.service;

import com.mcargo.deliveryservice.application.dto.DeliverySearchParam;
import com.mcargo.deliveryservice.domain.model.DeliveryStatusEnum;
import com.mcargo.deliveryservice.presentation.request.ReqDeliveryDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryDetailDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryStatusDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeliveryService {
    public ResDeliveryDto createDelivery(ReqDeliveryDto reqDeliveryDto) {

        ResDeliveryDto resDeliveryDto = ResDeliveryDto.builder()
                .orderId(reqDeliveryDto.orderId())
                .fromHubId(reqDeliveryDto.fromHubId())
                .toHubId(reqDeliveryDto.toHubId())
                .address(reqDeliveryDto.address())
                .receiverUserId(reqDeliveryDto.receiverUserId())
                .receiverSlackId(reqDeliveryDto.receiverSlackId())
                .build();

        return resDeliveryDto;
    }

    public ResDeliveryStatusDto updateDeliveryStatus(UUID deliveryId, DeliveryStatusEnum deliveryStatus) {
        ResDeliveryStatusDto resDeliveryStatusDto = ResDeliveryStatusDto.builder()
                .deliveryId(deliveryId)
                .deliveryStatus(deliveryStatus)
                .build();

        return resDeliveryStatusDto;
    }

    public Page<ResDeliveryDetailDto> getDeliveries(Pageable pageable) {
        return null;
    }

    public ResDeliveryDetailDto getDeliveryDetail(UUID deliveryId) {
        return null;
    }

    public Page<ResDeliveryDetailDto> searchDeliveries(Pageable pageable, DeliverySearchParam deliverySearchParam) {
        return null;
    }
}
