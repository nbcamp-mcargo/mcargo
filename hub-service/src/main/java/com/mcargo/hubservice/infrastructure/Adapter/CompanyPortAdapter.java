package com.mcargo.hubservice.infrastructure.Adapter;

import com.mcargo.hubservice.application.port.CompanyPort;
import com.mcargo.hubservice.infrastructure.client.CompanyClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyPortAdapter implements CompanyPort {
    private final CompanyClient companyClient;


}
