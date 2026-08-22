package com.Collage.Management.CollageManagementSyatem.controllers;


import com.Collage.Management.CollageManagementSyatem.dtos.ProfessorDTO;
import com.Collage.Management.CollageManagementSyatem.dtos.SubjectsProfesorThought;
import com.Collage.Management.CollageManagementSyatem.services.ProfessorService;
import com.Collage.Management.CollageManagementSyatem.services.ProfessorServiceInterface;
import com.Collage.Management.CollageManagementSyatem.services.StudentProfessorServiceInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/professor")
public class ProfessorController {
    private final ProfessorServiceInterface professorServiceInterface;
    private final ProfessorService professorService;

    private final StudentProfessorServiceInterface studentProfessorServiceInterface;


    public ProfessorController(ProfessorServiceInterface professorServiceInterface, ProfessorService professorService, StudentProfessorServiceInterface studentProfessorServiceInterface) {
        this.professorServiceInterface = professorServiceInterface;
        this.professorService = professorService;
        this.studentProfessorServiceInterface = studentProfessorServiceInterface;
    }
    @GetMapping("/{professorId}")
    private ProfessorDTO getProfessorById(@PathVariable Long professorId){
        return professorServiceInterface.getProfessorById(professorId);
    }


    @PostMapping
    private ProfessorDTO createProfessor(@RequestBody ProfessorDTO professorEntity){
        return professorServiceInterface.createProfessor(professorEntity);
    }
    @GetMapping
    public List<ProfessorDTO> getAllProfessors() {
        return professorServiceInterface.getAllProfessors();

    }

    @GetMapping("/{professorId}/getProfesorSubjects/{subjectId}")
    private ProfessorDTO getProfessorSubject(@PathVariable Long professorId,@PathVariable Long subjectId){
        return studentProfessorServiceInterface.getprofessorSubjects(professorId,subjectId);
    }

    @PutMapping("/{professorId}/subjectsTaugth/{subjectsId}")
    private ProfessorDTO assignProfessorToSubjects(@PathVariable Long professorId,
                                                      @PathVariable Long subjectsId){
        return professorServiceInterface.assignProfessorToSubjects(professorId,subjectsId);
    }

    @PutMapping("/{professorId}/studentsToProfessor/{studentId}")
    private ProfessorDTO assignStudentsToProfessor(@PathVariable Long professorId,
                                                      @PathVariable Long studentId){
        return professorServiceInterface.assignStudentsToProfessor(professorId,studentId);
    }

    @GetMapping("/subjectsProfThought/{profId}")
    private SubjectsProfesorThought subjectsProfesorThought(@PathVariable Long profId){
        return professorServiceInterface.subjectsProfTought(profId);
    }


}
