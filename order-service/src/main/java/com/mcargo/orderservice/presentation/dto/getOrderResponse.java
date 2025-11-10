package com.mcargo.orderservice.presentation.dto;

import com.mcargo.orderservice.domain.entity.Order;
import com.mcargo.orderservice.domain.entity.OrderProduct;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record getOrderResponse(
        UUID orderId,
        String status,
        String memo,
        Integer totalPrice,
        UUID provider_comp_id,
        UUID receiver_comp_id,
        UUID delivery_id,
        List<OrderProductResponse> orderProducts
) {

    public static getOrderResponse from(Order order) {
        List<OrderProductResponse> productResponses = order.getOrderProducts()
                .stream()
                .map(OrderProductResponse::from)
                .collect(Collectors.toList());

        return new getOrderResponse(
                order.getId(),
                order.getStatus(),
                order.getMemo(),
                order.getTotalPrice(),
                order.getProvider_comp_id(),
                order.getReceiver_comp_id(),
                order.getDelivery_id(),
                productResponses
        );
    }

    public record OrderProductResponse(
            UUID orderProductId,
            UUID hub_product_id,
            Integer quantity
    ) {
        public static OrderProductResponse from(OrderProduct orderProduct) {
            return new OrderProductResponse(
                    orderProduct.getId(),
                    orderProduct.getHub_product_id(),
                    orderProduct.getQuantity()
            );
        }
    }
}
