package com.microservice.course.microservice_course.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.course.microservice_course.persistence.entity.CourseEntity;

@Repository
public interface ICourseRepository extends CrudRepository<CourseEntity, Long> {
}
