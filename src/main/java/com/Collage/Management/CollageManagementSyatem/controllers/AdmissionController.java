package com.Collage.Management.CollageManagementSyatem.controllers;

import com.Collage.Management.CollageManagementSyatem.dtos.AdmissionDTO;
import com.Collage.Management.CollageManagementSyatem.services.AdmissionService;
import com.Collage.Management.CollageManagementSyatem.services.AdmissionService;
import com.Collage.Management.CollageManagementSyatem.services.AdmissionServiceInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/Admission")
public class AdmissionController {

    private final AdmissionServiceInterface admissionServiceInterface;
    private final AdmissionService admissinService;


    public AdmissionController(AdmissionServiceInterface admissionServiceInterface, AdmissionService admissinService1) {
        this.admissionServiceInterface = admissionServiceInterface;
        this.admissinService = admissinService1;
    }

    @GetMapping("/{admissionId}")
    private AdmissionDTO getAdmissionBydId(@PathVariable Long admissionId){
        return admissionServiceInterface.getAdmissionBydId(admissionId);
    }
    @GetMapping
    public List<AdmissionDTO> getAllAdmissions() {
        return  admissionServiceInterface.getAllAdmissions();
    }

    @PostMapping
    private  AdmissionDTO createAdmission(@RequestBody AdmissionDTO inputAdmission){
        return admissionServiceInterface.createAdmission(inputAdmission);
    }
    @PutMapping("/{admissionId}/admitted/{studentId}")
    private AdmissionDTO mapAdmissionToStudent(@PathVariable Long admissionId, @PathVariable Long studentId){
        return admissinService.mapAdmissionToStudent(admissionId,studentId);
    }
}
