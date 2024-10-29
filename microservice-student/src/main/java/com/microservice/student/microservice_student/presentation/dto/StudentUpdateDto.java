package com.microservice.student.microservice_student.presentation.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StudentUpdateDto {

    Long id;

    @Size(min = 2, message = "El nombre NO debe tener menos de dos caracteres")
    String name;

    @Size(min = 2, message = "El apellido NO debe tener menos de dos caracteres")
    String lastName;

    String email;

    Long courseId;

}
