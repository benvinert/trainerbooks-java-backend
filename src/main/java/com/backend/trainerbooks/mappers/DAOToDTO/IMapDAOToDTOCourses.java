package com.backend.trainerbooks.mappers.DAOToDTO;

import com.backend.trainerbooks.DTOS.CourseDTO;
import com.backend.trainerbooks.DTOS.CourseLectureDTO;
import com.backend.trainerbooks.entitys.CourseDAO;
import com.backend.trainerbooks.entitys.CourseLectureDAO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IMapDAOToDTOCourses {
    CourseDTO map(CourseDAO courseDAO);
    CourseLectureDTO map(CourseLectureDAO courseLectureDAO);
    List<CourseLectureDTO> map(List<CourseLectureDAO> courseLectureDAO);
}
