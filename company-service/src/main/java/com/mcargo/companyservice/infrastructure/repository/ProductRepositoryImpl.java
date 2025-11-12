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
    public void deleteById(String productId) {
        productJpaRepository.deleteById(productId);
    }

    @Override
    public List<Product> findAllByIdIn(List<String> productIds) {
        return productJpaRepository.findAllByIdIn(productIds);
    }

    @Override
    public Optional<Product> findByIdAndCompanyId(String productId, String companyId) {
        return productJpaRepository.findByIdAndCompanyId(productId, companyId);
    }

    @Override
    public void delete(Product product) {
        productJpaRepository.delete(product);
    }
}
