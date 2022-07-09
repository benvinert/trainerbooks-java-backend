package com.backend.trainerbooks.controllers;

import com.backend.trainerbooks.DTOS.CourseLectureDTO;
import com.backend.trainerbooks.annotations.SecuredEndPoint;
import com.backend.trainerbooks.entitys.CourseDAO;
import com.backend.trainerbooks.entitys.CourseLectureDAO;
import com.backend.trainerbooks.exceptions.NotFoundEntityException;
import com.backend.trainerbooks.exceptions.ValidationEntityException;
import com.backend.trainerbooks.jwt.JWTUtils;
import com.backend.trainerbooks.mappers.DAOToDTO.IMapDAOToDTOCourses;
import com.backend.trainerbooks.services.CourseLectureService;
import com.backend.trainerbooks.services.CourseService;
import com.backend.trainerbooks.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

import static com.backend.trainerbooks.enums.JWTEnum.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseLectureControllers {
    Logger logger = LoggerFactory.getLogger(CourseLectureControllers.class);

    private final JWTUtils jwtUtils;
    private final CourseLectureService courseLectureService;
    private final CourseService courseService;
    private final ValidationUtils validationUtils;
    private final IMapDAOToDTOCourses mapDAOToDTOCourses;


    @PostMapping("/add-lecture/{courseId}")
    public List<CourseLectureDTO> addLecture(HttpServletRequest request, HttpServletResponse response, @PathVariable Long courseId, @RequestBody CourseLectureDTO courseLectureDTO) {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        Optional<CourseDAO> optionalCourseDAO = courseService.findById(courseId);
        List<CourseLectureDTO> courseLectureDTOS = null;
        try {

            optionalCourseDAO.orElseThrow(() -> new NotFoundEntityException(String.format("Course Not found with Id : %s", courseId)));
            CourseDAO courseDAO = optionalCourseDAO.get();
            if (validationUtils.EntityRelatedToUser(courseDAO.getCreatedByTrainer().getAccountDAO().getUserDAO().getId(), userId)) {
                if (!validationUtils.isContainsURL(courseLectureDTO.getTitle()) && !validationUtils.isContainsURL(courseLectureDTO.getDescription())) {
                    boolean isTitleAlreadyExists = courseDAO.getCourseLectures().stream()
                            .anyMatch(eachCourseLectureDAO -> eachCourseLectureDAO.getTitle().equals(courseLectureDTO.getTitle()));
                    if (isTitleAlreadyExists) {
                        throw new ValidationEntityException(String.format("Cannot be duplicate lectures title , UserID : %s , Title of lecture is: %s", userId, courseLectureDTO.getTitle()));
                    }
                    CourseLectureDAO courseLectureDAO = CourseLectureDAO.builder().title(courseLectureDTO.getTitle()).description(courseLectureDTO.getDescription()).build();
                    courseDAO.getCourseLectures().add(courseLectureDAO);
                    courseDAO = courseService.save(courseDAO);
                    courseLectureDTOS = mapDAOToDTOCourses.map(courseDAO.getCourseLectures());
                } else {
                    throw new ValidationEntityException(String.format("Lecture details cannot contains URLs text , UserID : %s , Title of lecture is: %s", userId, courseLectureDTO.getTitle()));
                }
            } else {
                throw new ValidationEntityException(String.format("UserID : %s , don't have permission to add Lecture to course with ID : %s that owned to user with ID : %s", userId,courseId,courseDAO.getCreatedByTrainer().getAccountDAO().getUserDAO().getId()));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return courseLectureDTOS;
    }

    @SecuredEndPoint
    @PostMapping("/delete-lecture/{courseId}")
    public List<CourseLectureDTO> deleteLecture(HttpServletRequest request, HttpServletResponse response, @PathVariable Long courseId, @RequestBody CourseLectureDTO courseLectureDTO) {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        Optional<CourseDAO> optionalCourseDAO = courseService.findById(courseId);
        List<CourseLectureDTO> courseLectureDTOS = null;
        try {
            optionalCourseDAO.orElseThrow(() -> new NotFoundEntityException(String.format("Course Not found with Id : %s", courseId)));
            CourseDAO courseDAO = optionalCourseDAO.get();
            if (validationUtils.EntityRelatedToUser(courseDAO.getCreatedByTrainer().getAccountDAO().getUserDAO().getId(), userId)) {
                Optional<CourseLectureDAO> optionalCourseLectureDAO = courseDAO.getCourseLectures().stream().filter((eachCourseLecture) -> eachCourseLecture.getId().equals(courseLectureDTO.getId())).findFirst();
                optionalCourseLectureDAO.orElseThrow(() -> new NotFoundEntityException(String.format("CourseLecture Not found with Id : %s", courseLectureDTO.getId())));
                CourseLectureDAO courseLectureDAO = optionalCourseLectureDAO.get();
                courseDAO.getCourseLectures().remove(courseLectureDAO);
                courseService.save(courseDAO);
                courseLectureService.deleteLectureById(courseLectureDAO.getId());
            }else {
                throw new ValidationEntityException(String.format("UserID : %s , don't have permission to add Lecture to course with ID : %s that owned to user with ID : %s", userId,courseId,courseDAO.getCreatedByTrainer().getAccountDAO().getUserDAO().getId()));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return courseLectureDTOS;
    }
}
