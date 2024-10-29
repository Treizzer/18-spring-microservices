package com.microservice.student.microservice_student.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.microservice.student.microservice_student.presentation.dto.StudentDto;
import com.microservice.student.microservice_student.presentation.dto.StudentInsertDto;
import com.microservice.student.microservice_student.presentation.dto.StudentUpdateDto;
import com.microservice.student.microservice_student.service.interfaces.IStudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private IStudentService<StudentDto, StudentInsertDto, StudentUpdateDto> studentService;

    @GetMapping
    public ResponseEntity<List<StudentDto>> findAll() {
        return ResponseEntity.ok(this.studentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> findById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        var studentDto = this.studentService.findById(id);

        return studentDto == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(studentDto);
    }

    @PostMapping
    public ResponseEntity<StudentDto> save(@Valid @RequestBody StudentInsertDto studentInsertDto) {
        var studentDto = this.studentService.save(studentInsertDto);

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(studentDto.getId())
                        .toUri())
                .body(studentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> update(@Valid @RequestBody StudentUpdateDto studentUpdateDto,
            @PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        var studentDto = this.studentService.update(studentUpdateDto, id);

        return studentDto == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(studentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDto> deleteById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        var studentDto = this.studentService.deleteById(id);

        return studentDto == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(studentDto);
    }

    // @GetMapping("/find-by-course/{id}")
    // public ResponseEntity<List<StudentDto>> findByCourseId(@PathVariable(name =
    // "id") Long courseId) {
    @GetMapping("/find-by-course/{courseId}")
    public ResponseEntity<List<StudentDto>> findByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(this.studentService.findByCourseId(courseId));
    }

}
