package com.mcargo.hubservice.domain.entity;

import com.mcargo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_hub_route_sequence")
@Where(clause = "deleted_at IS NULL")
public class HubRouteSequence extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hub_route_sequence_id", columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "from_hub_id", referencedColumnName = "fromHubId"),
            @JoinColumn(name = "to_hub_id", referencedColumnName = "toHubId")
    })
    private HubRoute hubRoute;

    private int sequence;

    private UUID seqFromHubId;

    private UUID seqToHubId;

    // 생성
    public static HubRouteSequence create(HubRoute hubRoute, int sequence, UUID seqFromHubId, UUID seqToHubId) {
        HubRouteSequence seq = new HubRouteSequence();
        seq.hubRoute = hubRoute;
        seq.sequence = sequence;
        seq.seqFromHubId = seqFromHubId;
        seq.seqToHubId = seqToHubId;
        return seq;
    }
}
