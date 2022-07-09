package com.backend.trainerbooks.mappers.DTOToDAO;

import com.backend.trainerbooks.DTOS.CourseDTO;
import com.backend.trainerbooks.entitys.CourseDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IMapDTOToDAOCourses {
    CourseDAO map(CourseDTO courseDTO);
}
