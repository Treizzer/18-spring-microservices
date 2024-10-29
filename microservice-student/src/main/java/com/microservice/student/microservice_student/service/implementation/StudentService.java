package com.microservice.student.microservice_student.service.implementation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.student.microservice_student.persistence.entity.StudentEntity;
import com.microservice.student.microservice_student.persistence.repository.IStudentRepository;
import com.microservice.student.microservice_student.presentation.dto.StudentDto;
import com.microservice.student.microservice_student.presentation.dto.StudentInsertDto;
import com.microservice.student.microservice_student.presentation.dto.StudentUpdateDto;
import com.microservice.student.microservice_student.service.interfaces.IStudentService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentService implements IStudentService<StudentDto, StudentInsertDto, StudentUpdateDto> {

    @Autowired
    private IStudentRepository studentRepository;

    private static final ModelMapper MAPPER = new ModelMapper();

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> findAll() {
        var students = this.studentRepository.findAll();

        return StreamSupport
                .stream(students.spliterator(), false)
                .map(student -> MAPPER.map(student, StudentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDto findById(Long id) {
        var studentEntity = this.studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el estudiante con ID: " + id));

        return MAPPER.map(studentEntity, StudentDto.class);
    }

    @Override
    @Transactional
    public StudentDto save(StudentInsertDto insertDto) {
        try {
            var studentEntity = MAPPER.map(insertDto, StudentEntity.class);
            studentEntity = this.studentRepository.save(studentEntity);

            return MAPPER.map(studentEntity, StudentDto.class);

        } catch (Exception e) {
            throw new UnsupportedOperationException(
                    "Error al guardar un estudiante: " + insertDto + " -> e: " + e.toString());
        }
    }

    @Override
    @Transactional
    public StudentDto update(StudentUpdateDto updateDto, Long id) {
        var studentEntity = id != null
                ? this.studentRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("No se encontró al estudiante con ID: " + id))
                : this.studentRepository.findById(updateDto.getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "No se encontró al estudiante con ID: " + updateDto.getId()));

        if (!updateDto.getName().isBlank()) {
            studentEntity.setName(updateDto.getName());
        }
        if (!updateDto.getLastName().isBlank()) {
            studentEntity.setLastName(updateDto.getLastName());
        }
        if (!updateDto.getEmail().isBlank()) {
            studentEntity.setEmail(updateDto.getEmail());
        }
        if (updateDto.getCourseId() != null) {
            studentEntity.setCourseId(updateDto.getCourseId());
        }

        this.studentRepository.save(studentEntity);
        return MAPPER.map(studentEntity, StudentDto.class);
    }

    @Override
    @Transactional
    public StudentDto deleteById(Long id) {
        var studentEntity = this.studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró al estudiante con ID: " + id));

        // var studentDto = MAPPER.map(studentEntity, StudentDto.class);
        this.studentRepository.deleteById(id);

        return MAPPER.map(studentEntity, StudentDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> findByCourseId(Long courseId) {
        var students = this.studentRepository.findAllStudents(courseId);

        return StreamSupport
                .stream(students.spliterator(), false)
                .map(student -> MAPPER.map(student, StudentDto.class))
                .collect(Collectors.toList());
    }

}
