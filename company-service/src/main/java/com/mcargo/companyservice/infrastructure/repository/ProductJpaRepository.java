package com.mcargo.companyservice.infrastructure.repository;

import com.mcargo.companyservice.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, String> {
}
