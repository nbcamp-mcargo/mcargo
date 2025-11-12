package com.mcargo.companyservice.infrastructure.repository;

import com.mcargo.companyservice.domain.entity.Product;
import com.mcargo.companyservice.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Optional<Product> findById(String productId) {
        return productJpaRepository.findById(productId);
    }

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }
    @Override
    public void delete(Product product) {
        productJpaRepository.delete(product);
    }
}
