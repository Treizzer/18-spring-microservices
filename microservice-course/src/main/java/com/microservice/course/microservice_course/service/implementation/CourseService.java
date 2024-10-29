package com.microservice.course.microservice_course.service.implementation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.course.microservice_course.persistence.entity.CourseEntity;
import com.microservice.course.microservice_course.persistence.repository.ICourseRepository;
import com.microservice.course.microservice_course.presentation.client.IStudentClient;
import com.microservice.course.microservice_course.presentation.dto.CourseDto;
import com.microservice.course.microservice_course.presentation.dto.CourseInsertDto;
import com.microservice.course.microservice_course.presentation.dto.CourseUpdateDto;
import com.microservice.course.microservice_course.service.http.response.StudentByCourseResponse;
import com.microservice.course.microservice_course.service.interfaces.ICourseService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CourseService implements ICourseService<CourseDto, CourseInsertDto, CourseUpdateDto> {

    @Autowired
    private ICourseRepository courseRepository;

    @Autowired
    private IStudentClient studentClient;

    private static final ModelMapper MAPPER = new ModelMapper();

    @Override
    @Transactional(readOnly = true)
    public List<CourseDto> findAll() {
        var courses = this.courseRepository.findAll();

        return StreamSupport
                .stream(courses.spliterator(), false)
                .map(course -> MAPPER.map(course, CourseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDto findById(Long id) {
        var courseEntity = this.courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el curso con el ID: " + id));

        return MAPPER.map(courseEntity, CourseDto.class);
    }

    @Override
    @Transactional
    public CourseDto save(CourseInsertDto insertDto) {
        try {
            var courseEntity = MAPPER.map(insertDto, CourseEntity.class);
            courseEntity = this.courseRepository.save(courseEntity);

            return MAPPER.map(courseEntity, CourseDto.class);

        } catch (Exception e) {
            throw new UnsupportedOperationException(
                    "Eror al guardad un curso: " + insertDto + " -> e: " + e.toString());
        }
    }

    @Override
    @Transactional
    public CourseDto update(CourseUpdateDto updateDto, Long id) {
        var courseEntity = id != null
                ? this.courseRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("NO se encontro el curso con el ID: " + id))
                : this.courseRepository.findById(updateDto.getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "NO se encontro el curso con el ID: " + updateDto.getId()));

        if (!updateDto.getName().isBlank()) {
            courseEntity.setName(updateDto.getName());
        }
        if (!updateDto.getTeacher().isBlank()) {
            courseEntity.setTeacher(updateDto.getTeacher());
        }

        this.courseRepository.save(courseEntity);

        return MAPPER.map(courseEntity, CourseDto.class);
    }

    @Override
    @Transactional
    public CourseDto deleteById(Long id) {
        var courseEntity = this.courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el curso con el ID: " + id));

        this.courseRepository.deleteById(id);

        return MAPPER.map(courseEntity, CourseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentByCourseResponse findStudentsByCourseId(Long courseId) {
        var courseEntity = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el curso con el ID: " + courseId));

        var studentDtoList = this.studentClient.findByCourseId(courseId);

        return StudentByCourseResponse.builder()
                .name(courseEntity.getName())
                .teacher(courseEntity.getTeacher())
                .students(studentDtoList)
                .build();
    }

}
