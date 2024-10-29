package com.microservice.student.microservice_student.presentation.advice;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

}
