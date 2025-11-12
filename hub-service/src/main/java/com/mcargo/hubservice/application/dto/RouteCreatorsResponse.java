package com.mcargo.hubservice.application.dto;

import java.util.List;
import java.util.UUID;

public record RouteCreatorsResponse(
        UUID fromHubId,
        UUID toHubId,
        List<SequenceStep> sequenceSteps    // record 안에 다른 record 타입
) {
    public record SequenceStep(
            int sequence,
            UUID seqFromHubId,
            UUID seqToHubId
    ) {}
}