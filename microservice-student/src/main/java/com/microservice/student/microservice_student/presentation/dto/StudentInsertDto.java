package com.microservice.student.microservice_student.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StudentInsertDto {

    @NotBlank(message = "El nombre NO puede ser nulo o vacío")
    @Size(min = 2, message = "El nombre NO debe tener menos de dos caracteres")
    String name;

    @NotBlank(message = "El apellido NO puede ser nulo o vacío")
    @Size(min = 2, message = "El apellido NO debe tener menos de dos caracteres")
    String lastName;

    @NotBlank(message = "El email NO puede ser nulo o vacío")
    @Email(message = "El email NO tiene un formato correcto")
    String email;

    @NotNull
    Long courseId;

}

/*
 * @NotNull: El campo no debe ser null.
 * 
 * @NotEmpty: El campo no debe ser null y su tamaño debe ser mayor que cero.
 * 
 * @NotBlank: El campo no debe ser null y debe contener al menos un carácter que
 * no sea un espacio en blanco.
 * 
 * @Size: Controla el tamaño de cadenas, listas, arreglos, etc.
 * 
 * @Min y @Max: Define los valores mínimo y máximo para números.
 * 
 * @Email: Verifica si el campo tiene un formato de correo electrónico válido.
 * 
 * @Pattern: Define una expresión regular que el campo debe cumplir.
 */
