package com.microservice.course.microservice_course.presentation.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.course.microservice_course.presentation.dto.StudentDto;

// @FeignClient(name = "msvc-student", url = "localhost:8090/api/student")
@FeignClient(name = "msvc-student", url = "localhost:8080/api/student")
public interface IStudentClient {

    @GetMapping("/find-by-course/{courseId}")
    List<StudentDto> findByCourseId(@PathVariable Long courseId);

}
