package com.mcargo.companyservice.infrastructure.repository;

import com.mcargo.companyservice.domain.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyJpaRepository extends JpaRepository<Company, String> {

    Company save(Company company);

    Optional<Company> findById(String companyId);

    void deleteById(String companyId);

    boolean existsByName(String companyName);
}
