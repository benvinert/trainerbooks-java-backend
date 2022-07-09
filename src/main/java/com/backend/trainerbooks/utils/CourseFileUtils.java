package com.backend.trainerbooks.utils;

import com.backend.trainerbooks.DTOS.CourseFileDTO;
import com.backend.trainerbooks.entitys.CourseFileDAO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class CourseFileUtils {

    public Optional<CourseFileDAO> buildCourseFileDAOByCourseFileDTO(CourseFileDTO courseFileDTO) {
        Optional<CourseFileDAO> optionalCourseFileDAO = Optional.empty();
        if(StringUtils.hasText(courseFileDTO.getFileName())) {
            optionalCourseFileDAO = Optional.ofNullable(CourseFileDAO.builder()
                    .fileName(courseFileDTO.getFileName())
                    .type(courseFileDTO.getType())
                    .size(courseFileDTO.getSize())
                    .createdDate(ZonedDateTime.now()).build());
        }
        return optionalCourseFileDAO;
    }
}
