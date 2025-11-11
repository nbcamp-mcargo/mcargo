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
@Table(name = "p_hub_product")
@Where(clause = "deleted_at IS NULL")
public class HubProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hub_product_id", columnDefinition = "uuid")
    private UUID id;

    private UUID productId;

    private HubProductStatus status;

    private Integer stock;

    public static HubProduct create(UUID productId, Integer stock) {
        HubProduct hubProduct = new HubProduct();
        hubProduct.productId = productId;
        hubProduct.status = HubProductStatus.SALE;
        hubProduct.stock = stock;
        return hubProduct;
    }

    public void update(HubProductStatus status, Integer stock) {
        if (stock != null) this.stock = stock;
        if (status != null) this.status = status;
    }

}
