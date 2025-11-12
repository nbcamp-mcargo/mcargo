package com.mcargo.companyservice.presentation.controller;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.companyservice.application.response.CompanyResponseCode;
import com.mcargo.companyservice.application.service.CompanyService;
import com.mcargo.companyservice.domain.entity.Company;
import com.mcargo.companyservice.presentation.dto.request.CompanyCreateRequest;
import com.mcargo.companyservice.presentation.dto.request.CompanyUpdateRequest;
import com.mcargo.companyservice.presentation.dto.response.CompanyCreateResponse;
import com.mcargo.companyservice.presentation.dto.response.CompanyReadResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.mcargo.companyservice.application.response.CompanyResponseCode.*;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/company")
    public ApiResponse<CompanyCreateResponse> createCompany(
            @Valid @RequestBody CompanyCreateRequest request) {

        Company createdCompany = companyService.createCompany(request);

        return ApiResponse.of(COMPANY_CREATE_SUCCESS, createdCompany.toCreateResponse());
    }

    @GetMapping("/company/{companyId}")
    public CompanyReadResponse getCompany(
            @PathVariable String companyId) {

        return companyService.getCompany(companyId);
    }

    @PatchMapping("/company/{companyId}")
    public ApiResponse<CompanyReadResponse> updateCompany(
            @PathVariable String companyId,
            @NotNull @RequestParam Long userId,
            @Valid @RequestBody CompanyUpdateRequest request) {

        companyService.updateCompany(companyId, userId, request);

        return ApiResponse.of(COMPANY_UPDATE_SUCCESS);
    }

    @DeleteMapping("/company/{companyId}")
    public ApiResponse<CompanyReadResponse> deleteCompany(
            @PathVariable String companyId,
            @NotNull @RequestParam Long userId) {

        companyService.deleteCompany(companyId, userId);

        return ApiResponse.of(CompanyResponseCode.COMPANY_DELETE_SUCCESS);
    }
}
