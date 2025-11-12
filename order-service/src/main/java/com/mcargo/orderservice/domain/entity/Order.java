package com.mcargo.orderservice.domain.entity;

import com.mcargo.common.entity.BaseEntity;
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
@Table(name = "p_order")
@Where(clause = "deleted_at IS NULL")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", columnDefinition = "uuid")
    private UUID id;

    private String status;

    private String memo;

    private Integer totalPrice;

    private UUID provider_comp_id;

    private UUID receiver_comp_id;

    private UUID delivery_id;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id")
    private List<OrderProduct> OrderProducts;

    public static Order createOrder(String status, String memo, Integer totalPrice) {
        Order order = new Order();
        order.status = status;
        order.memo = memo;
        order.totalPrice = totalPrice;
        return order;
    }

    public void addOrderProducts(List<OrderProduct> orderProducts) {
        this.OrderProducts = orderProducts;
    }

    public void updateOrder(String status, String memo, Integer totalPrice) {
        this.status = status;
        this.memo = memo;
        this.totalPrice = totalPrice;
    }
}
