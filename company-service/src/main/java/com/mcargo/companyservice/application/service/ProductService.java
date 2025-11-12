package com.mcargo.companyservice.application.service;

import com.mcargo.companyservice.application.exceptiopn.CompanyException;
import com.mcargo.companyservice.application.exceptiopn.ProductException;
import com.mcargo.companyservice.domain.entity.Company;
import com.mcargo.companyservice.domain.entity.Product;
import com.mcargo.companyservice.domain.repository.CompanyRepository;
import com.mcargo.companyservice.domain.repository.ProductRepository;
import com.mcargo.companyservice.presentation.dto.request.ProductCreateRequest;
import com.mcargo.companyservice.presentation.dto.request.ProductUpdateRequest;
import com.mcargo.companyservice.presentation.dto.response.ProductReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.mcargo.companyservice.application.response.CompanyResponseCode.COMPANY_NOT_FOUND;
import static com.mcargo.companyservice.application.response.ProductResponseCode.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CompanyRepository companyRepository;

    public void createProduct(ProductCreateRequest request) {
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));

        Product product = request.toEntity(company);

        productRepository.save(product);
    }

    public ProductReadResponse getProduct(String productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND));

        return product.toProductReadResponse(product);
    }

    public void updateProduct(String productId, ProductUpdateRequest request) {

        companyRepository.findById(request.companyId())
                .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND));

        product.update(request);
    }

    public void deleteProduct(String productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND));

        productRepository.delete(product);
    }
}
