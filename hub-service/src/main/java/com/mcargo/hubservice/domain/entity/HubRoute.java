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
@Table(name = "p_hub_route")
@Where(clause = "deleted_at IS NULL")
public class HubRoute extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hub_route_id", columnDefinition = "uuid")
    private UUID id;

    private UUID fromHubId;

    private UUID toHubId;

//    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//    @JoinColumn(name = "hub_route_id") // 외래키를 상대 테이블에 생성
//    private List<HubRouteSequence> hubRouteSequences;

    public static HubRoute create(UUID fromHubId, UUID toHubId){
        HubRoute hubRoute = new HubRoute();
        hubRoute.fromHubId = fromHubId;
        hubRoute.toHubId = toHubId;
        return hubRoute;
    }
}
