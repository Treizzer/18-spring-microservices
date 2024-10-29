package com.microservice.course.microservice_course.service.interfaces;

import java.util.List;

import com.microservice.course.microservice_course.service.http.response.StudentByCourseResponse;

public interface ICourseService<T, TI, TU> {

    List<T> findAll();

    T findById(Long id);

    T save(TI insertDto);

    T update(TU updateDto, Long id);

    T deleteById(Long id);

    StudentByCourseResponse findStudentsByCourseId(Long courseId);

}
