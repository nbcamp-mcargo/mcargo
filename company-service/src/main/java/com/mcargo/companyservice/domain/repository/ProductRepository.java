package com.mcargo.companyservice.domain.repository;

import com.mcargo.companyservice.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(String id);

    void deleteById(String productId);

    List<Product> findAllByIdIn(List<String> productIds);

    Optional<Product> findByIdAndCompanyId(String productId, String companyId);

    void delete(Product product);
}
