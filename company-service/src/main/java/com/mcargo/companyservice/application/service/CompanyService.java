package com.mcargo.companyservice.application.service;

import com.mcargo.companyservice.application.exceptiopn.CompanyException;
import com.mcargo.companyservice.domain.entity.Company;
import com.mcargo.companyservice.domain.repository.CompanyRepository;
import com.mcargo.companyservice.presentation.dto.request.CompanyCreateRequest;
import com.mcargo.companyservice.presentation.dto.request.CompanyUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mcargo.companyservice.application.response.CompanyResponseCode.COMPANY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company getCompany(String companyId) {

        // 예외 로직 추가 필요

        return companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));
    }

    public void createCompany(CompanyCreateRequest request) {

        // 예외 로직 추가 필요

        companyRepository.save(request.toEntity());
    }

    public void deleteCompany(String companyId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));

        companyRepository.deleteById(companyId);
    }

    public void updateCompany(String companyId, CompanyUpdateRequest request) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));

        company.update(request);
    }
}
