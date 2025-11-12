package com.mcargo.orderservice.domain.rerpository;

import com.mcargo.orderservice.domain.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Order save(Order newOrder);

    Optional<Order> findById(UUID orderId);

    Page<Order> findAll(Pageable pageable);
}
