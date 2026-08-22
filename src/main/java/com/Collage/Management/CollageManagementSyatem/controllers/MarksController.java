package com.Collage.Management.CollageManagementSyatem.controllers;


import com.Collage.Management.CollageManagementSyatem.dtos.MarksDTO;
import com.Collage.Management.CollageManagementSyatem.services.MarksServiceInterface;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marks")
public class MarksController {

    private MarksServiceInterface marksServiceInterface;

    public MarksController(MarksServiceInterface marksServiceInterface) {
        this.marksServiceInterface = marksServiceInterface;
    }

    @PostMapping("/postMarks")
            private MarksDTO addMarks( @RequestBody MarksDTO dto)  {
return marksServiceInterface.addMarksForSubjectToStudent(dto);


    }

}
