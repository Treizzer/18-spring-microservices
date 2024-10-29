package com.microservice.student.microservice_student.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.student.microservice_student.persistence.entity.StudentEntity;

@Repository
public interface IStudentRepository extends CrudRepository<StudentEntity, Long> {

    // @Query("SELECT s FROM StudentEntity s WHERE s.courseId = ?1")
    @Query("SELECT s FROM StudentEntity s WHERE s.courseId = :courseId")
    Iterable<StudentEntity> findAllStudents(Long courseId);

    // Iterable<StudentEntity> findByCourseId(Long courseId);

}
