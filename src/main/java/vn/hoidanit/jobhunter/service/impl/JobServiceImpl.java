package vn.hoidanit.jobhunter.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.response.*;
import vn.hoidanit.jobhunter.repository.JobRepository;
import vn.hoidanit.jobhunter.repository.SkillRepository;
import vn.hoidanit.jobhunter.service.JobService;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    private JobRepository jobRepository;
    private SkillRepository skillRepository;

    public JobServiceImpl(JobRepository jobRepository, SkillRepository skillRepository) {
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public Job handleCreateJob(Job job) {
        return this.jobRepository.save(job);
    }

    @Override
    public ResCreateJobDTO convertToResCreateJobDTO(Job job) {
        ResCreateJobDTO convertJob = new ResCreateJobDTO();

        convertJob.setId(job.getId());
        convertJob.setName(job.getName());
        convertJob.setLocation(job.getLocation());
        convertJob.setSalary(job.getSalary());
        convertJob.setQuantity(job.getQuantity());
        convertJob.setLevel(job.getLevel());
        convertJob.setStartDate(job.getStartDate());
        convertJob.setEndDate(job.getEndDate());
        convertJob.setSkills(job.getSkills().stream().map(Skill::getName).toList());
        convertJob.setCreatedAt(job.getCreatedAt());
        convertJob.setCreatedBy(job.getCreatedBy());
        convertJob.setActive(job.isActive());
        return convertJob;
    }

    @Override
    public ResUpdateJobDTO convertToResUpdateJobDTO(Job job) {
        ResUpdateJobDTO convertJob = new ResUpdateJobDTO();

        convertJob.setId(job.getId());
        convertJob.setName(job.getName());
        convertJob.setLocation(job.getLocation());
        convertJob.setSalary(job.getSalary());
        convertJob.setQuantity(job.getQuantity());
        convertJob.setLevel(job.getLevel());
        convertJob.setStartDate(job.getStartDate());
        convertJob.setEndDate(job.getEndDate());
        convertJob.setSkills(job.getSkills().stream().map(Skill::getName).toList());
        convertJob.setUpdatedAt(job.getUpdatedAt());
        convertJob.setUpdatedBy(job.getUpdatedBy());
        convertJob.setActive(job.isActive());
        return convertJob;
    }

    @Override
    public Job handleGetJobById(Long id) {
        Optional<Job> jobOptional = this.jobRepository.findById(id);
        return jobOptional.orElse(null);
    }

    @Override
    public Job handleUpdateJob(Job job) {

        Job checkJob = this.handleGetJobById(job.getId());
        //check skill
        if (job.getSkills() != null) {
            List<Long> reqSkill = job.getSkills().stream().map(x -> x.getId()).toList();
            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkill);
            job.setSkills(dbSkills);
        }

        if (checkJob != null) {

            checkJob.setId(job.getId());
            checkJob.setName(job.getName());
            checkJob.setLocation(job.getLocation());
            checkJob.setSalary(job.getSalary());
            checkJob.setQuantity(job.getQuantity());
            checkJob.setLevel(job.getLevel());
            checkJob.setDescription(job.getDescription());
            checkJob.setStartDate(job.getStartDate());
            checkJob.setEndDate(job.getEndDate());
            checkJob.setActive(job.isActive());
            checkJob.setSkills(job.getSkills());
            this.jobRepository.save(checkJob);
        }
        return checkJob;
    }

    @Override
    public ResultPaginationDTO handleGetAllJob(Specification<Job> spec, Pageable pageable) {
        Page<Job> pageJob = this.jobRepository.findAll(spec, pageable);
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        ResultPaginationDTO result = new ResultPaginationDTO();

        meta.setPage(pageable.getPageNumber() + 1); // trang hien tai
        meta.setPageSize(pageable.getPageSize());// trang nay co bn nhieu phan tu

        meta.setPages(pageJob.getTotalPages());
        meta.setTotal(pageJob.getTotalElements());

        result.setMeta(meta);
        result.setResult(pageJob.getContent());

        return result;


    }

    @Override
    public void handleDeleteJob(Long id) {
        this.jobRepository.deleteById(id);
    }


}
