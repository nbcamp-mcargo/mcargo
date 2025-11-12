package com.mcargo.orderservice.domain.entity;

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
@Table(name = "p_order_product")
@Where(clause = "deleted_at IS NULL")
public class OrderProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_product_id", columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false)
    private UUID hubProductId;

    @Column(nullable = false)
    private String productName;

    private int price;

    private String description;

    @Column(nullable = false)
    private Integer quantity;

    public static OrderProduct create(UUID hubProductId, String productName, int price, String description, Integer quantity) {
        OrderProduct op = new OrderProduct();
        op.hubProductId = hubProductId;
        op.productName = productName;
        op.price = price;
        op.description = description;
        op.quantity = quantity;
        return op;
    }

    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
