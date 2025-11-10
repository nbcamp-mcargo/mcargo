package com.mcargo.orderservice.application.service;

import com.mcargo.common.exception.OrderException;
import com.mcargo.common.response.OrderResponseCode;
import com.mcargo.common.util.PageingUtils;
import com.mcargo.orderservice.domain.entity.Order;
import com.mcargo.orderservice.domain.entity.OrderProduct;
import com.mcargo.orderservice.domain.rerpository.OrderRepository;
import com.mcargo.orderservice.presentation.dto.CreateOrderRequest;
import com.mcargo.orderservice.presentation.dto.getOrderResponse;
import com.mcargo.orderservice.presentation.dto.SearchOrderRequest;
import com.mcargo.orderservice.presentation.dto.UpdateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    // 주문 생성
    @Transactional
    public UUID createOrder(CreateOrderRequest request) {


        Order newOrder = Order.createOrder(
                request.status(),
                request.memo(),
                request.totalPrice()
        );

        List<OrderProduct> orderProducts = request.orderProducts().stream()
                .map(p -> OrderProduct.create(p.hub_product_id(), p.quantity()))
                .collect(Collectors.toList());

        newOrder.getOrderProducts().addAll(orderProducts);

        Order saved = orderRepository.save(newOrder);
        return saved.getId();
    }

    // 주문 목록 조회
    @Transactional(readOnly = true)
    public Page<getOrderResponse> getOrderAll(int page, int size, String sortBy, Boolean isDescending) {
        Pageable pageable = PageingUtils.createPageable(page, size, sortBy, isDescending);

        Page<Order> pageOrders = orderRepository.findAll(pageable);

        return pageOrders.map(order ->
                getOrderResponse.from(order)
        );
    }

    // 주문 상세 조회
    @Transactional(readOnly = true)
    public getOrderResponse readOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderResponseCode.ORDER_NOT_FOUND));

        return getOrderResponse.from(order);
    }

    // 주문 수정
    @Transactional
    public void updateOrder(UUID orderId, UpdateOrderRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderResponseCode.ORDER_NOT_FOUND));

        order.updateOrder(
                request.status(),
                request.memo(),
                request.totalPrice()
        );
    }

    // 주문 삭제
    @Transactional
    public void deleteOrder(Long userId, UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderResponseCode.ORDER_NOT_FOUND));

        order.delete(userId);
    }

//    // 주문 검색
//    @Transactional(readOnly = true)
//    public Page<getOrderResponse> searchOrder(SearchOrderRequest request) {
//        // TODO: 검색 조건에 맞춰 구현
//        return Page.empty();
//    }
}
