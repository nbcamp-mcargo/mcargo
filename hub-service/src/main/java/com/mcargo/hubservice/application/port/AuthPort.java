package com.mcargo.hubservice.application.port;

import java.util.UUID;

public interface AuthPort {

    //허브 배송 담당자 호출. 파라미터 있으면 업체배송
    UUID getNextDriver(UUID hubId);
}
