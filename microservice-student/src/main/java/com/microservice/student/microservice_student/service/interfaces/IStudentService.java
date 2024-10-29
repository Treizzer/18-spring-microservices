package com.microservice.student.microservice_student.service.interfaces;

import java.util.List;

// Maybe I need check or look if is viable make an
// ICommonService or if is most effective create an
// IStudentService, after all this microservice
// is for students
public interface IStudentService<T, TI, TU> {

    List<T> findAll();

    T findById(Long id);

    T save(TI insertDto);

    T update(TU updateDto, Long id);

    T deleteById(Long id);

    List<T> findByCourseId(Long courseId);

}
