package vn.hoidanit.jobhunter.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.dto.Meta;
import vn.hoidanit.jobhunter.domain.dto.ResCompanyDTO;
import vn.hoidanit.jobhunter.domain.dto.ResUserDTO;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.CompanyRepository;
import vn.hoidanit.jobhunter.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);

    }

    @Override
    public Company handleUpdateCompany(Company reqCompany) {
        Optional<Company> comCheck = companyRepository.findById(reqCompany.getId());

        if (comCheck.isPresent()) {
            Company company = comCheck.get();
            company.setDescription(reqCompany.getDescription());
            company.setAddress(reqCompany.getAddress());
            company.setLogo(reqCompany.getLogo());
            company.setName(reqCompany.getName());

            return this.companyRepository.save(company);

        }
        return null;

    }

    @Override
    public ResultPaginationDTO handleGetCompany(Specification<Company> spec, Pageable pageable) {

        Page<Company> pageCom = this.companyRepository.findAll(spec, pageable);
        Meta meta = new Meta();
        ResultPaginationDTO result = new ResultPaginationDTO();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());

        meta.setPages(pageCom.getTotalPages());
        meta.setTotal(pageCom.getTotalElements());

        result.setMeta(meta);
        // result.setResult(pageCom.getContent());
        result.setResult(pageCom.getContent()
                .stream().map(item -> new ResCompanyDTO(
                        item.getId(),
                        item.getName(),
                        item.getAddress(),
                        item.getCreatedAt(),
                        item.getUpdatedAt()))
                .collect(Collectors.toList()));

        return result;
    }

    @Override
    public void handleDeleteCompany(Long id) {

        this.companyRepository.deleteById(id);

    }

}
