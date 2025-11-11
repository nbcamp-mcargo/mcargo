package com.mcargo.companyservice.presentation.controller;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.companyservice.application.service.ProductService;
import com.mcargo.companyservice.presentation.dto.request.ProductCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ApiResponse<Void> createProduct(ProductCreateRequest request) {

        productService.createProduct(request);

        return null;
    }


    @GetMapping
    public ApiResponse<Void> getProduct() {
        return null;
    }

    @GetMapping
    public ApiResponse<Void> getProductList() {
        return null;
    }

    @PutMapping
    public ApiResponse<Void> updateProduct() {
        return null;
    }

    @DeleteMapping
    public ApiResponse<Void> deleteProduct() {
        return null;
    }
}
