package com.microservice.course.microservice_course.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CourseInsertDto {

    @NotBlank(message = "El nombre NO debe ser nulo o vacío")
    @Size(min = 2, message = "El nombre NO debe tener menos de dos caracteres")
    String name;

    @NotBlank(message = "El maestro NO debe ser nulo o vacío")
    @Size(min = 2, message = "El maestro NO debe tener menos de dos caracteres")
    String teacher;

}
