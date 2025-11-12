package com.mcargo.companyservice.presentation.controller;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.companyservice.application.response.ProductResponseCode;
import com.mcargo.companyservice.application.service.ProductService;
import com.mcargo.companyservice.presentation.dto.request.ProductCreateRequest;
import com.mcargo.companyservice.presentation.dto.request.ProductUpdateRequest;
import com.mcargo.companyservice.presentation.dto.response.ProductCreateResponse;
import com.mcargo.companyservice.presentation.dto.response.ProductReadResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.mcargo.companyservice.application.response.ProductResponseCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product")
    public ApiResponse<ProductCreateResponse> createProduct(
            @Valid @RequestBody ProductCreateRequest request) {

        ProductCreateResponse createdProduct = productService.createProduct(request);

        return ApiResponse.of(PRODUCT_CREATE_SUCCESS, createdProduct);
    }

    @GetMapping("/product/{productId}")
    public ProductReadResponse getProduct(
            @PathVariable String productId) {

        return productService.getProduct(productId);
    }

    @PatchMapping("/product/{productId}")
    public ApiResponse<ProductResponseCode> updateProduct(
            @PathVariable String productId,
            @RequestParam Long userId,
            @Valid @RequestBody ProductUpdateRequest request) {

        productService.updateProduct(productId, userId, request);

        return ApiResponse.of(PRODUCT_UPDATE_SUCCESS);
    }

    @DeleteMapping("/product/{productId}")
    public ApiResponse<ProductResponseCode> deleteProduct(
            @PathVariable String productId,
            @RequestParam Long userId) {

        productService.deleteProduct(productId, userId);

        return ApiResponse.of(PRODUCT_DELETE_SUCCESS);
    }
}
