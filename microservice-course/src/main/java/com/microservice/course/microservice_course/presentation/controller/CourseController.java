package com.microservice.course.microservice_course.presentation.controller;

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

import com.microservice.course.microservice_course.presentation.dto.CourseDto;
import com.microservice.course.microservice_course.presentation.dto.CourseInsertDto;
import com.microservice.course.microservice_course.presentation.dto.CourseUpdateDto;
import com.microservice.course.microservice_course.service.http.response.StudentByCourseResponse;
import com.microservice.course.microservice_course.service.interfaces.ICourseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private ICourseService<CourseDto, CourseInsertDto, CourseUpdateDto> courseService;

    @GetMapping
    public ResponseEntity<List<CourseDto>> findAll() {
        return ResponseEntity.ok(this.courseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> findById(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        var courseDto = this.courseService.findById(id);

        return courseDto == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(courseDto);
    }

    @PostMapping
    public ResponseEntity<CourseDto> save(@Valid @RequestBody CourseInsertDto courseInsertDto) {
        var courseDto = this.courseService.save(courseInsertDto);

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(courseDto.getId())
                        .toUri())
                .body(courseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> update(@Valid @RequestBody CourseUpdateDto courseUpdateDto,
            @PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        var courseDto = this.courseService.update(courseUpdateDto, id);

        return courseDto == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(courseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CourseDto> deleteById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        var courseDto = this.courseService.deleteById(id);

        return courseDto == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(courseDto);
    }

    @GetMapping("/find-student/{courseId}")
    public ResponseEntity<StudentByCourseResponse> findStudentsByCourseId(@PathVariable Long courseId) {
        if (courseId == null || courseId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        var studentsCourse = this.courseService.findStudentsByCourseId(courseId);

        return studentsCourse == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(studentsCourse);
    }

}
