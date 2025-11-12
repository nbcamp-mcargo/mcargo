package com.mcargo.orderservice.infrastructure.jpa;

import com.mcargo.orderservice.domain.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<Order, UUID> {

    Order save(Order newOrder);

    Optional<Order> findById(UUID orderId);

    Page<Order> findAll(Pageable pageable);

}
