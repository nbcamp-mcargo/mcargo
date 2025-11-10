package com.mcargo.orderservice.application.service;

import com.mcargo.common.exception.OrderException;
import com.mcargo.common.response.OrderResponseCode;
import com.mcargo.orderservice.domain.entity.Order;
import com.mcargo.orderservice.domain.entity.OrderProduct;
import com.mcargo.orderservice.domain.rerpository.OrderRepository;
import com.mcargo.orderservice.presentation.dto.CreateOrderProductRequest;
import com.mcargo.orderservice.presentation.dto.UpdateOrderProductRequest;
import com.mcargo.orderservice.presentation.dto.getOrderResponse.OrderProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderProductService {

    private final OrderRepository orderRepository;

    // 주문 상품 생성
    @Transactional
    public UUID createOrderProduct(UUID orderId, CreateOrderProductRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderResponseCode.ORDER_NOT_FOUND));

        OrderProduct orderProduct = OrderProduct.create(
                request.hub_product_id(),
                request.quantity()
        );

        order.getOrderProducts().add(orderProduct);

        return orderProduct.getId();
    }

    // 주문 상품 목록 조회
    @Transactional(readOnly = true)
    public List<OrderProductResponse> getOrderProducts(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderResponseCode.ORDER_NOT_FOUND));

        return order.getOrderProducts()
                .stream()
                .map(OrderProductResponse::from)
                .collect(Collectors.toList());
    }

    // 주문 상품 상세 조회
    @Transactional(readOnly = true)
    public OrderProductResponse getOrderProduct(UUID orderId, UUID orderProductId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderResponseCode.ORDER_NOT_FOUND));

        return order.getOrderProducts()
                .stream()
                .filter(op -> op.getId().equals(orderProductId))
                .findFirst()
                .map(OrderProductResponse::from)
                .orElseThrow(() -> new OrderException(OrderResponseCode.ORDER_PRODUCT_NOT_FOUND));
    }

    // 주문 상품 수정
    @Transactional
    public void updateOrderProduct(UUID orderId, UUID orderProductId, UpdateOrderProductRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderResponseCode.ORDER_NOT_FOUND));

        OrderProduct target = order.getOrderProducts()
                .stream()
                .filter(op -> op.getId().equals(orderProductId))
                .findFirst()
                .orElseThrow(() -> new OrderException(OrderResponseCode.ORDER_PRODUCT_NOT_FOUND));

        target.updateQuantity(request.quantity());
    }

    // 주문 상품 삭제
    @Transactional
    public void deleteOrderProduct(UUID orderId, UUID orderProductId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderResponseCode.ORDER_NOT_FOUND));

        Iterator<OrderProduct> iterator = order.getOrderProducts().iterator();
        boolean removed = false;

        while (iterator.hasNext()) {
            OrderProduct op = iterator.next();
            if (op.getId().equals(orderProductId)) {
                iterator.remove();
                removed = true;
                break;
            }
        }

        if (!removed) {
            throw new OrderException(OrderResponseCode.ORDER_PRODUCT_NOT_FOUND);
        }
    }
}
