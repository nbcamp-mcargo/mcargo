package com.mcargo.deliveryservice.application.service;

import com.mcargo.deliveryservice.application.dto.DeliveryRouteSearchParam;
import com.mcargo.deliveryservice.domain.model.DeliveryRouteStatusEnum;
import com.mcargo.deliveryservice.presentation.request.ReqDeliveryRouteStatusDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryRouteDetailDto;
import com.mcargo.deliveryservice.presentation.response.ResDeliveryRouteStatusDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeliveryRouteService {
    public ResDeliveryRouteStatusDto updateDeliveryStatus(UUID deliveryRouteId, DeliveryRouteStatusEnum deliveryRouteStatus, ReqDeliveryRouteStatusDto reqDeliveryRouteStatusDto) {
        ResDeliveryRouteStatusDto resDeliveryRouteStatusDto = new ResDeliveryRouteStatusDto();
        return resDeliveryRouteStatusDto;
    }

    public Page<ResDeliveryRouteDetailDto> getDeliveryRoutes(Pageable pageable) {
        return null;
    }

    public Page<ResDeliveryRouteDetailDto> searchDeliveryRoutes(Pageable pageable, DeliveryRouteSearchParam deliveryRouteSearchParam) {
        return null;
    }
}
