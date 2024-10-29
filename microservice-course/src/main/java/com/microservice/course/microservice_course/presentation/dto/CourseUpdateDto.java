package com.microservice.course.microservice_course.presentation.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CourseUpdateDto {

    Long id;

    @Size(min = 2, message = "El nombre NO debe tener menos de dos caracteres")
    String name;

    @Size(min = 2, message = "El maestro NO debe tener menos de dos caracteres")
    String teacher;

}
