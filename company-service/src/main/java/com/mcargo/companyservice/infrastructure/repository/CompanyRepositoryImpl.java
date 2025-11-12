package com.mcargo.companyservice.infrastructure.repository;

import com.mcargo.companyservice.domain.entity.Company;
import com.mcargo.companyservice.domain.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository {

    private final CompanyJpaRepository companyJpaRepository;

    @Override
    public Company save(Company company) {
        return companyJpaRepository.save(company);
    }

    @Override
    public Optional<Company> findById(String id) {
        return companyJpaRepository.findById(id);
    }

    @Override
    public void deleteById(String companyId) {
        companyJpaRepository.deleteById(companyId);
    }

    @Override
    public boolean existsById(String companyId) {
        return companyJpaRepository.existsById(companyId);
    }
}
