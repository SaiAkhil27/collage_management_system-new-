package com.Collage.Management.CollageManagementSyatem.controllers;


import com.Collage.Management.CollageManagementSyatem.dtos.SubjectDTO;
import com.Collage.Management.CollageManagementSyatem.entiites.SubjectEntity;
import com.Collage.Management.CollageManagementSyatem.services.SubjectService;
import com.Collage.Management.CollageManagementSyatem.services.SubjectServiceInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/subject")
public class SubjectController {
   private final SubjectService subjectService;

   private final SubjectServiceInterface subjectServiceInterface;

    public SubjectController(SubjectService subjectService, SubjectServiceInterface subjectServiceInterface) {
        this.subjectService = subjectService;
        this.subjectServiceInterface = subjectServiceInterface;
    }

    @GetMapping
    private List<SubjectDTO> getAllSubjects(){
        return subjectServiceInterface.getAllSubjects();
    }


    @GetMapping("/{subjectId}")
    private SubjectDTO getSubjectById(@PathVariable Long subjectId){
        return subjectServiceInterface.getSubjectById(subjectId);
    }
    @PostMapping
    private SubjectDTO createSubject(@RequestBody SubjectDTO subjectEntity){
        return subjectServiceInterface.createSubject(subjectEntity);
    }

}
