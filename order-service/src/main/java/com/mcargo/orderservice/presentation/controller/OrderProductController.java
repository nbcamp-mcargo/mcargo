package com.mcargo.orderservice.presentation.controller;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.common.response.OrderResponseCode;
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

    @PostMapping
    public ApiResponse<Void> createOrderProduct(
            @PathVariable UUID orderId,
            @RequestBody @Valid CreateOrderProductRequest request
    ) {
        UUID orderProductId = orderProductService.createOrderProduct(orderId, request);
        return ApiResponse.of(OrderResponseCode.ORDER_PRODUCT_CREATED);
    }

    @GetMapping
    public ApiResponse<List<OrderProductResponse>> getOrderProducts(
            @PathVariable UUID orderId
    ) {
        List<OrderProductResponse> responses = orderProductService.getOrderProducts(orderId);
        return ApiResponse.of(OrderResponseCode.ORDER_PRODUCT_FOUND);
    }

    @GetMapping("/{orderProductId}")
    public ApiResponse<OrderProductResponse> getOrderProduct(
            @PathVariable UUID orderId,
            @PathVariable UUID orderProductId
    ) {
        OrderProductResponse response = orderProductService.getOrderProduct(orderId, orderProductId);
        return ApiResponse.of(OrderResponseCode.ORDER_PRODUCT_FOUND);
    }

    @PutMapping("/{orderProductId}")
    public ApiResponse<Void> updateOrderProduct(
            @PathVariable UUID orderId,
            @PathVariable UUID orderProductId,
            @RequestBody @Valid UpdateOrderProductRequest request
    ) {
        orderProductService.updateOrderProduct(orderId, orderProductId, request);
        return ApiResponse.of(OrderResponseCode.ORDER_PRODUCT_UPDATED);
    }

    @DeleteMapping("/{orderProductId}")
    public ApiResponse<Void> deleteOrderProduct(
            @PathVariable UUID orderId,
            @PathVariable UUID orderProductId
    ) {
        orderProductService.deleteOrderProduct(orderId, orderProductId);
        return ApiResponse.of(OrderResponseCode.ORDER_PRODUCT_DELETED);
    }
}
