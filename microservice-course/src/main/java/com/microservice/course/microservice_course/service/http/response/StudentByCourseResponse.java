package com.microservice.course.microservice_course.service.http.response;

import java.util.List;

import com.microservice.course.microservice_course.presentation.dto.StudentDto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StudentByCourseResponse {

    String name;
    String teacher;
    List<StudentDto> students;

}
