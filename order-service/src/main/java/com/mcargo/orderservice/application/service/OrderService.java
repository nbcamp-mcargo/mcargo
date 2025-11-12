package com.mcargo.orderservice.application.service;

import com.mcargo.orderservice.domain.exception.OrderException;
import com.mcargo.orderservice.domain.response.OrderResponseCode;
import com.mcargo.common.util.PageingUtils;
import com.mcargo.orderservice.domain.entity.Order;
import com.mcargo.orderservice.domain.entity.OrderProduct;
import com.mcargo.orderservice.domain.rerpository.OrderRepository;
import com.mcargo.orderservice.infrastructure.client.CompanyClient;
import com.mcargo.orderservice.infrastructure.client.DeliveryClient;
import com.mcargo.orderservice.infrastructure.client.HubClient;
import com.mcargo.orderservice.infrastructure.client.SlackClient;
import com.mcargo.orderservice.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final DeliveryClient deliveryClient;
    private final CompanyClient companyClient;
    private final HubClient hubClient;
    private final SlackClient slackClient;

    // 주문 생성
    @Transactional
    public Object createOrder(CreateOrderRequest request) {

        // 재고 확인
        List<GetHubProductOrderableResponse> orderAble = hubClient.getHubProductOrderable(request.orderProducts());
        if (orderAble.stream().anyMatch(
                o -> !o.orderable()))
        {
            return orderAble;
        }
        System.out.println(orderAble);

        AtomicReference<String> hubId = new AtomicReference<>(); //허브id
        AtomicReference<String> pName = new AtomicReference<>(); //상품명
        //주문 등록
        List<OrderProduct> saveProducts = new ArrayList<>();
        request.orderProducts().forEach(orderItem -> {
            ProductReadResponse res = companyClient.getProduct(orderItem.productId());

            hubId.set(res.hubId());
            pName.set(res.name());
            OrderProduct tempItem = OrderProduct.create(
                    orderItem.hubProductId(),
                    res.name(),
                    res.price(),
                    res.description(),
                    orderItem.quantity()
            );
            saveProducts.add(tempItem);
        });

        Order newOrder = Order.createOrder(
                request.memo(),
                saveProducts
        );

        Order order = orderRepository.save(newOrder);

        // 배송 생성 요청
        ReqDeliveryDto deliveryReq = new ReqDeliveryDto(
                order.getId(),
                UUID.fromString(hubId.get()),
                UUID.fromString("e7305b38-ec4b-41af-87c4-01168f5579c6"), // 수령업체가 속한 허브id 부산으로 고정
                "부산광역시 수영구 광안해변로 203",  // 수령업체 주소
                1L,                             // 공급업체 유저ID. 유저가 구현이 안됨
                "gyoseok17@kakao.com",        // 수령인 슬랙 email
                UUID.fromString("7f9c1ac7-4c37-4721-a8b8-a7f989106a3c") // 수령업체id
                );
        ResDeliveryDto deliveryRes = deliveryClient.createDelivery(deliveryReq);

        // 메세지 전송 요청
        if(!slackClient.sendMessage(new AiMessageRequest(
                deliveryReq.receiverSlackId(),
                        "주문시간: " + order.getCreatedAt() + "\n" +
                        "상품정보: " + pName.toString() + "\n" +
                        "요청사항: " + request.memo() + "\n" +
                        "발송지: 서울 특별시\n" +
                        "도착지: 부산광역시 수영구 광안해변로 203\n"
                )
        )) { return "슬랙메시지 송신 에러"; }

        return "주문이 요청과 발송 기한 메시지가 송신 되엇습니다.";
    }

    // 주문 목록 조회
    @Transactional(readOnly = true)
    public Page<getOrderResponse> getOrderAll(int size, String sortBy, Boolean isDescending) {
        Pageable pageable = PageingUtils.createPageable(size, sortBy, isDescending);

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

        // 배송 시작 됬는지 확인
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

}
