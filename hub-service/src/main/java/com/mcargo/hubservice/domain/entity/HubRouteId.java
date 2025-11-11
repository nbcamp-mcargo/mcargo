package com.mcargo.hubservice.domain.entity;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class HubRouteId implements Serializable {
    //복합키 엔티티

    private UUID fromHubId;
    private UUID toHubId;

}