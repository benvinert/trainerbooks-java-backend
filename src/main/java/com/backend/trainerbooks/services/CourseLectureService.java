package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.CourseLectureDAO;
import com.backend.trainerbooks.repositorys.CourseLectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseLectureService {

    private final CourseLectureRepository courseLectureRepository;

    public Optional<CourseLectureDAO> findById(Long id) {
        return courseLectureRepository.findById(id);
    }

    public CourseLectureDAO save(CourseLectureDAO courseLectureDAO) {
        return courseLectureRepository.save(courseLectureDAO);
    }

    public void deleteLectureById(Long id) {
        courseLectureRepository.deleteById(id);
    }
}
