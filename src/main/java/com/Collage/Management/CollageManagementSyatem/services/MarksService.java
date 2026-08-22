package com.Collage.Management.CollageManagementSyatem.services;

import com.Collage.Management.CollageManagementSyatem.dtos.MarksDTO;
import com.Collage.Management.CollageManagementSyatem.entiites.Exam;
import com.Collage.Management.CollageManagementSyatem.entiites.Marks;
import com.Collage.Management.CollageManagementSyatem.entiites.StudentEntity;
import com.Collage.Management.CollageManagementSyatem.entiites.SubjectEntity;
import com.Collage.Management.CollageManagementSyatem.exceptions.ResourceNotFoundException;
import com.Collage.Management.CollageManagementSyatem.repositories.ExamRepository;
import com.Collage.Management.CollageManagementSyatem.repositories.MarksRepository;
import com.Collage.Management.CollageManagementSyatem.repositories.StudentRepository;
import com.Collage.Management.CollageManagementSyatem.repositories.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MarksService implements MarksServiceInterface {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final MarksRepository marksRepository;
    private final ExamRepository examRepository;
    private final ModelMapper modelMapper;

    public MarksService(StudentRepository studentRepository,
                        SubjectRepository subjectRepository,
                        MarksRepository marksRepository,
                        ExamRepository examRepository,
                        ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.marksRepository = marksRepository;
        this.examRepository = examRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MarksDTO addMarksForSubjectToStudent(MarksDTO dto) {
        StudentEntity student = studentRepository.findById(dto.getStudent_id())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + dto.getStudent_id()));

        SubjectEntity subject = subjectRepository.findById(dto.getSubject_id())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject not found with id: " + dto.getSubject_id()));

        Exam exam = examRepository.findById(dto.getExam_id())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Exam not found with id: " + dto.getExam_id()));

        Marks marks = new Marks();
        marks.setStudent(student);
        marks.setSubject(subject);
        marks.setExam(exam);
        marks.setMarks(Math.toIntExact(dto.getMarks()));
        Marks saved = marksRepository.save(marks);

        MarksDTO result = new MarksDTO();
        result.setId(saved.getId());
        result.setStudent_id(student.getId());
        result.setSubject_id(subject.getId());
        result.setExam_id(exam.getId());
        result.setMarks((int) saved.getMarks().longValue()); // ✅ converts Integer back to Long for DTO
        return result;
    }
}