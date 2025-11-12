package com.mcargo.orderservice.presentation.controller;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.orderservice.domain.response.OrderResponseCode;
import com.mcargo.orderservice.application.service.OrderService;
import com.mcargo.orderservice.presentation.dto.CreateOrderRequest;
import com.mcargo.orderservice.presentation.dto.getOrderResponse;
import com.mcargo.orderservice.presentation.dto.UpdateOrderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private OrderService orderService;

    // 주문 생성은 업체 담당자만 가능
    @PostMapping
    public ApiResponse<Void> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        orderService.createOrder(request);
        return ApiResponse.of(OrderResponseCode.ORDER_CREATED);
    }

    // 주문 목록 조회
    @GetMapping
    public ApiResponse<Page<getOrderResponse>> getOrderAll(
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "createAt") String sortBy,
                                                           @RequestParam(defaultValue = "true") Boolean isDescending) {
        return ApiResponse.of(OrderResponseCode.ORDER_FOUND, orderService.getOrderAll(size, sortBy, isDescending));
    }

    // 주문 상세 조회
    @GetMapping("/{orderId}")
    public ApiResponse<getOrderResponse> readOrder(@PathVariable UUID orderId) {
        orderService.readOrder(orderId);
        return ApiResponse.of(OrderResponseCode.ORDER_FOUND);
    }

    // 주문 수정
    @PatchMapping("/{orderId}")
    public ApiResponse<Void> updateOrder(@PathVariable UUID orderId, @RequestBody UpdateOrderRequest request) {
        orderService.updateOrder(orderId, request);
        return ApiResponse.of(OrderResponseCode.ORDER_UPDATED);
    }

    //주문 삭제는 수령 업체 담당자만 가능
    @DeleteMapping("/{orderId}")
    public ApiResponse<Void> deleteOrder(@PathVariable UUID orderId) {
        Long userId = 1L;
        orderService.deleteOrder(userId, orderId);
        return ApiResponse.of(OrderResponseCode.ORDER_DELETED);
    }

}
