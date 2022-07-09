package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.CourseDAO;
import com.backend.trainerbooks.repositorys.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseDAO save(CourseDAO courseDAO) {
        return courseRepository.save(courseDAO);
    }

    public Optional<CourseDAO> findById(Long courseId) {
        return courseRepository.findById(courseId);
    }
}
