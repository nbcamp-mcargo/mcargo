package com.mcargo.orderservice.presentation.controller;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.orderservice.domain.response.OrderResponseCode;
import com.mcargo.orderservice.application.service.OrderProductService;
import com.mcargo.orderservice.presentation.dto.CreateOrderProductRequest;
import com.mcargo.orderservice.presentation.dto.UpdateOrderProductRequest;
import com.mcargo.orderservice.presentation.dto.getOrderResponse.OrderProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders/{orderId}/products")
public class OrderProductController {

    private final OrderProductService orderProductService;

//    // 주문 상품 생성
//    @PostMapping
//    public ApiResponse<Void> createOrderProduct(
//            @PathVariable UUID orderId,
//            @RequestBody @Valid CreateOrderProductRequest request
//    ) {
//        UUID orderProductId = orderProductService.createOrderProduct(orderId, request);
//        return ApiResponse.of(OrderResponseCode.ORDER_PRODUCT_CREATED);
//    }

    // 주문 상품 목록 조회
    @GetMapping
    public ApiResponse<List<OrderProductResponse>> getOrderProducts(
            @PathVariable UUID orderId
    ) {
        List<OrderProductResponse> responses = orderProductService.getOrderProducts(orderId);
        return ApiResponse.of(OrderResponseCode.ORDER_PRODUCT_FOUND);
    }

    // 주문 상품 상세 조회
    @GetMapping("/{orderProductId}")
    public ApiResponse<OrderProductResponse> getOrderProduct(
            @PathVariable UUID orderId,
            @PathVariable UUID orderProductId
    ) {
        OrderProductResponse response = orderProductService.getOrderProduct(orderId, orderProductId);
        return ApiResponse.of(OrderResponseCode.ORDER_PRODUCT_FOUND);
    }

    // 주문 상품 수정
    @PutMapping("/{orderProductId}")
    public ApiResponse<Void> updateOrderProduct(
            @PathVariable UUID orderId,
            @PathVariable UUID orderProductId,
            @RequestBody @Valid UpdateOrderProductRequest request
    ) {
        orderProductService.updateOrderProduct(orderId, orderProductId, request);
        return ApiResponse.of(OrderResponseCode.ORDER_PRODUCT_UPDATED);
    }

    // 주문 상품 삭제
    @DeleteMapping("/{orderProductId}")
    public ApiResponse<Void> deleteOrderProduct(
            @PathVariable UUID orderId,
            @PathVariable UUID orderProductId
    ) {
        orderProductService.deleteOrderProduct(orderId, orderProductId);
        return ApiResponse.of(OrderResponseCode.ORDER_PRODUCT_DELETED);
    }
}
