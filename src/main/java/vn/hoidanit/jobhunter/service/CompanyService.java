package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;

public interface CompanyService {
    Company handleCreateCompany(Company company);

    Company handleUpdateCompany(Company reqCompany);

    ResultPaginationDTO handleGetCompany(Specification<Company> spec, Pageable pageable);

    void handleDeleteCompany(Long id);

    Optional<Company> findById(Long id);

}
