package com.mcargo.hubservice.application.util;

import com.mcargo.hubservice.domain.entity.Hub;
import com.mcargo.hubservice.infrastructure.kakaomap.dto.GetDistanceAndDurationResponse;

public interface RouteUtils {

    GetDistanceAndDurationResponse getDistanceAndDuration(Hub fromHub, Hub toHub);
}
