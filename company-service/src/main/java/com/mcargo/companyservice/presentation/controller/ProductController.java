package com.mcargo.companyservice.presentation.controller;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.companyservice.application.response.ProductResponseCode;
import com.mcargo.companyservice.application.service.ProductService;
import com.mcargo.companyservice.presentation.dto.request.ProductCreateRequest;
import com.mcargo.companyservice.presentation.dto.request.ProductUpdateRequest;
import com.mcargo.companyservice.presentation.dto.response.ProductReadResponse;
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
    public ApiResponse<ProductResponseCode> createProduct(
            @RequestBody ProductCreateRequest request) {

        productService.createProduct(request);

        return ApiResponse.of(PRODUCT_CREATE_SUCCESS);
    }

    @GetMapping("/product/{productId}")
    public ApiResponse<ProductReadResponse> getProduct(
            @PathVariable String productId,
            @RequestParam String companyId) {

        ProductReadResponse response = productService.getProduct(productId, companyId);

        return ApiResponse.of(PRODUCT_READ_SUCCESS, response);
    }

    @PatchMapping("/product/{productId}")
    public ApiResponse<ProductResponseCode> updateProduct(
            @PathVariable String productId,
            @RequestBody ProductUpdateRequest request) {

        productService.updateProduct(productId, request);

        return ApiResponse.of(PRODUCT_UPDATE_SUCCESS);
    }

    @DeleteMapping("/product/{productId}")
    public ApiResponse<ProductResponseCode> deleteProduct(
            @PathVariable String productId,
            @RequestParam String companyId) {

        productService.deleteProduct(productId, companyId);

        return ApiResponse.of(PRODUCT_DELETE_SUCCESS);
    }
}
