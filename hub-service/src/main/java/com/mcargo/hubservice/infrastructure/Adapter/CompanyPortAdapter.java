package com.mcargo.hubservice.infrastructure.Adapter;

import com.mcargo.hubservice.application.port.CompanyPort;
import com.mcargo.hubservice.infrastructure.client.CompanyClient;
import com.mcargo.hubservice.presentation.dto.CompanyReadResponse;
import com.mcargo.hubservice.presentation.dto.ProductReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CompanyPortAdapter implements CompanyPort {

    private final CompanyClient companyClient;

    @Override
    public ProductReadResponse getProduct(UUID productId) {
        return companyClient.getProduct(productId);
    }

    @Override
    public CompanyReadResponse getCompany(UUID companyId) {
        return companyClient.getCompany(companyId);
    }
}
