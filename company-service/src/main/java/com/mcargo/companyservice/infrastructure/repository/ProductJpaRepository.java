package com.mcargo.companyservice.infrastructure.repository;

import com.mcargo.companyservice.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product, String> {
    List<Product> findAllByIdIn(List<String> productIds);
}
