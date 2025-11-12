package com.mcargo.companyservice.application.service;

import com.mcargo.companyservice.application.exceptiopn.CompanyException;
import com.mcargo.companyservice.domain.entity.Company;
import com.mcargo.companyservice.domain.repository.CompanyRepository;
import com.mcargo.companyservice.presentation.dto.request.CompanyCreateRequest;
import com.mcargo.companyservice.presentation.dto.request.CompanyUpdateRequest;
import com.mcargo.companyservice.presentation.dto.response.CompanyReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mcargo.companyservice.application.response.CompanyResponseCode.COMPANY_NAME_DUPLICATED;
import static com.mcargo.companyservice.application.response.CompanyResponseCode.COMPANY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    public Company createCompany(CompanyCreateRequest request) {

        if (companyRepository.existsByName(request.name())) {
            throw new CompanyException(COMPANY_NAME_DUPLICATED);
        }

        return companyRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public CompanyReadResponse getCompany(String companyId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));

        return company.toReadResponse();
    }

    @Transactional
    public void deleteCompany(String companyId, Long userId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));

        company.delete(userId);
    }

    @Transactional
    public void updateCompany(String companyId, Long userId, CompanyUpdateRequest request) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));

        company.updateEntity(request);
    }
}
