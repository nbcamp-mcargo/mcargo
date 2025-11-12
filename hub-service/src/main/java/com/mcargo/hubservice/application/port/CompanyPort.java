package com.mcargo.hubservice.application.port;

import com.mcargo.hubservice.presentation.dto.CompanyReadResponse;
import com.mcargo.hubservice.presentation.dto.ProductReadResponse;

import java.util.UUID;

public interface CompanyPort {

    // 상품 정보 요청
    ProductReadResponse getProduct(UUID productId);

    // 업체 정보 요청
    CompanyReadResponse getCompany(UUID companyId);
}
