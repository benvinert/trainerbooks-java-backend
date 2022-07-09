package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.CourseFileDAO;
import com.backend.trainerbooks.repositorys.CourseFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseFileService {

    private final CourseFileRepository courseFileRepository;

    public void deleteCourseFile(CourseFileDAO courseFileDAO) {
        courseFileRepository.delete(courseFileDAO);
    }
}
