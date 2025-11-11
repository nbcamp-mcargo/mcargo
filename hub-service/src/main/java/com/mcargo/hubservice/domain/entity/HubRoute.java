package com.mcargo.hubservice.domain.entity;

import com.mcargo.common.entity.BaseEntity;
import com.mcargo.hubservice.application.dto.RouteCreatorsResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_hub_route")
@Where(clause = "deleted_at IS NULL")
@IdClass(HubRouteId.class)
public class HubRoute extends BaseEntity {

    @Id
    private UUID fromHubId;

    @Id
    private UUID toHubId;

    @OneToMany(mappedBy = "hubRoute", cascade = CascadeType.ALL)
    private List<HubRouteSequence> hubRouteSequences = new ArrayList<>();;

    // 생성
    public static HubRoute create(UUID fromHubId, UUID toHubId, List<RouteCreatorsResponse.SequenceStep> sequences) {
        HubRoute hubRoute = new HubRoute();
        hubRoute.fromHubId = fromHubId;
        hubRoute.toHubId = toHubId;

        for (RouteCreatorsResponse.SequenceStep seq : sequences) {
            HubRouteSequence hubRouteSequence = HubRouteSequence.create(hubRoute, seq.sequence(), seq.seqFromHubId(), seq.seqToHubId());
            hubRoute.hubRouteSequences.add(hubRouteSequence);
        }

        return hubRoute;
    }

}