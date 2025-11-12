package com.mcargo.companyservice.domain.repository;

import com.mcargo.companyservice.domain.entity.Product;

import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(String id);

    void delete(Product product);
}
