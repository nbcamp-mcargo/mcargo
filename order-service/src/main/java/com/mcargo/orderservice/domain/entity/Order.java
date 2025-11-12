package com.mcargo.orderservice.domain.entity;

import com.mcargo.common.entity.BaseEntity;
import com.mcargo.orderservice.presentation.dto.CreateOrderProductRequest;
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

    private UUID providerCompId; // 공급업체 아이디

    private UUID receiverCompId; // 수령업체 아이디

    private String receiverAddress;

    private UUID delivery_id;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id")
    private List<OrderProduct> orderProducts;

    public static Order createOrder(String memo, List<OrderProduct> orderProducts) {
        Order order = new Order();
        order.memo = memo;
        order.orderProducts = orderProducts;

        int priceSum = 0;
        for (OrderProduct orderProduct : orderProducts) {
            priceSum += orderProduct.getPrice();
        }
        order.totalPrice = priceSum;
        order.status = "재고 확인 대기 중";
        return order;
    }

    public void addOrderProducts(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
    }

    public void updateOrder(String status, String memo, Integer totalPrice) {
        this.status = status;
        this.memo = memo;
        this.totalPrice = totalPrice;
    }
}
