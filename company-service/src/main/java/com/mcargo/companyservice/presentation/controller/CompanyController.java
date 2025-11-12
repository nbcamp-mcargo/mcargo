package com.mcargo.companyservice.presentation.controller;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.companyservice.application.service.CompanyService;
import com.mcargo.companyservice.domain.entity.Company;
import com.mcargo.companyservice.presentation.dto.request.CompanyCreateRequest;
import com.mcargo.companyservice.presentation.dto.response.CompanyReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.mcargo.companyservice.application.response.CompanyResponseCode.COMPANY_CREATE_SUCCESS;
import static com.mcargo.companyservice.application.response.CompanyResponseCode.COMPANY_READ_SUCCESS;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/company")
    public ApiResponse<Void> createCompany(
            @RequestBody CompanyCreateRequest request) {

        companyService.createCompany(request);

        return ApiResponse.of(COMPANY_CREATE_SUCCESS);
    }

    @GetMapping("/company/{companyId}")
    public ApiResponse<CompanyReadResponse> getCompany(
            @PathVariable String companyId) {

        Company company = companyService.getCompany(companyId);

        return ApiResponse.of(COMPANY_READ_SUCCESS, company.toCompanyReadResponse());
    }
}
