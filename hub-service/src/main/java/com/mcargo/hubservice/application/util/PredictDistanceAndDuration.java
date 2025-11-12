package com.mcargo.hubservice.application.util;

import com.mcargo.hubservice.domain.entity.Hub;
import com.mcargo.hubservice.application.dto.GetDistanceAndDurationResponse;

public interface PredictDistanceAndDuration {

    // 출발, 도착 허브를 받아서 예상 시간, 거리 반환
    GetDistanceAndDurationResponse getDistanceAndDuration(Hub fromHub, Hub toHub);
}
