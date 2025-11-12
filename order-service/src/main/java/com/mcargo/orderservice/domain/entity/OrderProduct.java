package com.mcargo.orderservice.domain.entity;

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
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_product_id", columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false)
    private UUID hub_product_id;

    @Column(nullable = false)
    private Integer quantity;

    public static OrderProduct create(UUID hub_product_id, Integer quantity) {
        OrderProduct op = new OrderProduct();
        op.hub_product_id = hub_product_id ;
        op.quantity = quantity;
        return op;
    }

    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
