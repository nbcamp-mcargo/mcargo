package com.mcargo.companyservice.application.service;

import com.mcargo.companyservice.domain.repository.ProductRepository;
import com.mcargo.companyservice.presentation.dto.request.ProductCreateRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    public void createProduct(ProductCreateRequest request) {

    }
}
