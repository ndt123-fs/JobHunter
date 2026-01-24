package vn.hoidanit.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.SkillService;
import vn.hoidanit.jobhunter.utils.anotations.ApiMessage;
import vn.hoidanit.jobhunter.utils.error.EmailInvalidException;
import vn.hoidanit.jobhunter.utils.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1")
@Validated
public class SkillController {
    private SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/skills")
    @ApiMessage("Create new skill !")
    public ResponseEntity<Skill> createNewSkill(@Valid @RequestBody  Skill skill) throws EmailInvalidException {
        boolean checkName = this.skillService.checkNameSkill(skill.getName());
        if (checkName) {
            throw new EmailInvalidException("Skill " + skill.getName() + " da ton tai, vui long nhap skill khac ! ");
        }
        Skill newSkill = this.skillService.handleCreateSkill(skill);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(newSkill);


    }
    @PutMapping("/skills")
    @ApiMessage("Update a skill !")
    public ResponseEntity<Skill> updateASkill(@Valid @RequestBody Skill skill)  throws EmailInvalidException,IdInvalidException{

        Skill checkId = this.skillService.checkId(skill.getId());
        if(checkId == null){
            throw  new IdInvalidException("Id " + skill.getId() + " khong ton tai , vui long nhan id hop le !");
        }
        boolean checkName  = this.skillService.checkNameSkill(skill.getName());
        if(checkName) {
            throw new EmailInvalidException("Skill " + skill.getName() + " da ton tai, vui long nhap skill khac ! ");
        }
        Skill newSkill = this.skillService.handleUpdateSkill(skill);
        return ResponseEntity.status(HttpStatus.OK.value()).body(newSkill);
    }
    @GetMapping("/skills")
    @ApiMessage("Get all skill !")
    public ResponseEntity<ResultPaginationDTO> getAllSkill(@Filter Specification<Skill> spec, Pageable pageable) {
        ResultPaginationDTO result =  this.skillService.handleGetAllSkill(spec,pageable);
        return ResponseEntity.status(HttpStatus.OK.value()).body(result);
    }
    @DeleteMapping("/skills/{id}")
    @ApiMessage("Delete a skill !")
    public ResponseEntity<Void> deleteSkill(@PathVariable("id") long id){
        this.skillService.handleDeleteSkill(id);
        return ResponseEntity.status(HttpStatus.OK.value()).body(null);
    }
}
