package com.mcargo.companyservice.domain.repository;

import com.mcargo.companyservice.domain.entity.Company;

import java.util.Optional;

public interface CompanyRepository {

    Company save(Company company);

    Optional<Company> findById(String companyId);

    void deleteById(String companyId);
}
