package com.microservice.course.microservice_course.presentation.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StudentDto {

    Long id;
    String name;
    String lastName;
    String email;
    Long courseId;

}
