package com.mcargo.hubservice.domain.entity;

import com.mcargo.common.entity.BaseEntity;
import com.mcargo.common.exception.HubException;
import com.mcargo.common.response.HubResponseCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_hub")
@Where(clause = "deleted_at IS NULL")
public class Hub extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hub_id", columnDefinition = "uuid")
    private UUID id;

    private String name;

    private String address;

    private Double latitude;

    private Double longitude;

    private Integer lastDriverNumber; // 마지막 업체 배송 담당자 번호

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "hub_id") // 외래키를 상대 테이블에 생성
    private List<HubProduct> hubProducts;

    //허브 생성
    public static Hub create(String name, String address, Double latitude, Double longitude) {
        Hub hub = new Hub();
        hub.name = name;
        hub.address = address;
        hub.latitude = latitude;
        hub.longitude = longitude;
        hub.lastDriverNumber = 0; // 생성시 0이면 다음 번호 계산시 1
        return hub;
    }

    // 허브 데이터 수정 메서드
    public void update(String name, String address, Double latitude, Double longitude, Integer lastDriverNumber) {
        if (name != null) this.name = name;
        if (address != null) this.address = address;
        if (latitude != null) this.latitude = latitude;
        if (longitude != null) this.longitude = longitude;
        if (lastDriverNumber != null) this.lastDriverNumber = lastDriverNumber;
    }

    // 허브 기사 업데이트
    public void updateDriverNumber(Integer driverNumber) {
        if (driverNumber != null) this.lastDriverNumber = driverNumber;
    }

    //ㅡㅡ허브상품 관련ㅡㅡ
    // 허브상품 추가
    public void addHubProduct(UUID productId, Integer stock) {
        HubProduct hubProduct = HubProduct.create(productId, stock);
        hubProducts.add(hubProduct);
    }

    //허브상품 수정
    public void updateHubProduct(UUID productId, HubProductStatus status, Integer stock) {
        hubProducts.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst().orElseThrow(
                        () -> new HubException(HubResponseCode.HUB_PRODUCT_NOT_FOUND))
                .update(status, stock);
    }

    //허브상품 삭제
    public void deleteHubProduct(Long userId, UUID productId) {
        hubProducts.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst().orElseThrow(
                        () -> new HubException(HubResponseCode.HUB_PRODUCT_NOT_FOUND))
                .delete(userId);
    }

}
