package com.backend.trainerbooks.controllers;

import com.backend.trainerbooks.DTOS.CourseDTO;
import com.backend.trainerbooks.DTOS.CourseFileDTO;
import com.backend.trainerbooks.DTOS.CourseLectureDTO;
import com.backend.trainerbooks.annotations.SecuredEndPoint;
import com.backend.trainerbooks.entitys.CourseDAO;
import com.backend.trainerbooks.entitys.CourseFileDAO;
import com.backend.trainerbooks.entitys.CourseLectureDAO;
import com.backend.trainerbooks.entitys.TrainerDAO;
import com.backend.trainerbooks.enums.CourseStatus;
import com.backend.trainerbooks.exceptions.NotFoundEntityException;
import com.backend.trainerbooks.jwt.JWTUtils;
import com.backend.trainerbooks.mappers.DAOToDTO.IMapDAOToDTOCourses;
import com.backend.trainerbooks.mappers.DTOToDAO.IMapDTOToDAOCourses;
import com.backend.trainerbooks.services.CourseFileService;
import com.backend.trainerbooks.services.CourseLectureService;
import com.backend.trainerbooks.services.CourseService;
import com.backend.trainerbooks.services.TrainerAccountService;
import com.backend.trainerbooks.utils.CourseFileUtils;
import com.backend.trainerbooks.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.backend.trainerbooks.enums.JWTEnum.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CoursesControllers {
    Logger logger = LoggerFactory.getLogger(CoursesControllers.class);

    private final TrainerAccountService trainerAccountService;
    private final IMapDTOToDAOCourses mapDTOToDAOCourses;
    private final IMapDAOToDTOCourses mapDAOToDTOCourses;
    private final CourseService courseService;
    private final JWTUtils jwtUtils;
    private final CourseFileUtils courseFileDAO;
    private final ValidationUtils validationUtils;
    private final CourseLectureService courseLectureService;
    private final CourseFileService courseFileService;

    @SecuredEndPoint
    @PostMapping("/create-trainer-course-with-held-status")
    public CourseDTO createTrainerCourse(HttpServletRequest request, HttpServletResponse response, @RequestBody CourseDTO courseDTO) throws NotFoundEntityException {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));

        CourseDAO courseDAO = mapDTOToDAOCourses.map(courseDTO);
        if (courseDAO.getCreatedByTrainer() != null && Objects.equals(userId, courseDTO.getCreatedByTrainer().getUserId())) {
            TrainerDAO trainerDAO = trainerAccountService.findTrainerAccountByTrainerId(courseDTO.getCreatedByTrainer().getId());
            if (trainerDAO != null) {
                courseDAO.setCreatedDate(ZonedDateTime.now());
                courseDAO.setCreatedByTrainer(trainerDAO);
                courseDAO.setCourseStatus(CourseStatus.DRAFT);
                //Init Lectures
                courseDAO.setCourseLectures(new LinkedList<>());
                CourseLectureDAO courseLectureDAO = new CourseLectureDAO();
                courseLectureDAO.setTitle("Introduction");
                courseDAO.getCourseLectures().add(courseLectureDAO);
                //
                courseDAO = courseService.save(courseDAO);
            } else {
                logger.error(String.format("Not found Entity trainerDAO with  ID: %s", courseDTO.getCreatedByTrainer().getId()));
                throw new NotFoundEntityException(String.format("Not found Entity trainerDAO with  ID: %s", courseDTO.getCreatedByTrainer().getId()));
            }
        }
        return mapDAOToDTOCourses.map(courseDAO);
    }

    @SecuredEndPoint
    @GetMapping("/get-course-by-id/{courseId}")
    public CourseDTO getCourseById(HttpServletRequest request, HttpServletResponse response, @PathVariable Long courseId) throws NotFoundEntityException {
        Optional<CourseDAO> courseDAOOptional = courseService.findById(courseId);
        courseDAOOptional.orElseThrow(() -> new NotFoundEntityException(String.format("Course Not found with Id : %s", courseId)));
        return mapDAOToDTOCourses.map(courseDAOOptional.get());
    }


    @SecuredEndPoint
    @PutMapping("/add-course-file/{courseId}")
    public CourseDTO addCourseFile(HttpServletRequest request, HttpServletResponse response, @PathVariable Long courseId, @RequestBody CourseLectureDTO courseLectureDTO) throws NotFoundEntityException {
        CourseDTO respCourseDTO = null;
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        Optional<CourseDAO> courseDAOOptional = courseService.findById(courseId);
        courseDAOOptional.orElseThrow(() -> new NotFoundEntityException(String.format("Course Not found with Id : %s", courseId)));
        CourseDAO courseDAO = courseDAOOptional.get();
        Long entityUserId = courseDAO.getCreatedByTrainer().getAccountDAO().getUserDAO().getId();
        if (validationUtils.EntityRelatedToUser(entityUserId, userId) && courseLectureDTO.getId() != null) {
            Optional<CourseLectureDAO> courseLectureDAOOptional = courseDAO.getCourseLectures().stream().filter(courseLecture -> courseLecture.getId().equals(courseLectureDTO.getId())).findFirst();
            courseLectureDAOOptional.orElseThrow(() -> new NotFoundEntityException(String.format("CourseLecture Not found with Id : %s", courseLectureDTO.getId())));

            CourseFileDTO courseFileDTO = courseLectureDTO.getCourseFileLecture();
            Optional<CourseFileDAO> optionalCourseFileDAO = courseFileDAO.buildCourseFileDAOByCourseFileDTO(courseFileDTO);
            optionalCourseFileDAO.orElseThrow(() -> new ValidationException(String.format("CourseFileDAO Cannot be with empty file name , courseLectureDTO id : %s , userId : %s", courseLectureDTO.getId(), userId)));
            CourseFileDAO courseFileDAO = optionalCourseFileDAO.get();

            CourseLectureDAO courseLectureDAO = courseLectureDAOOptional.get();
            CourseFileDAO courseFileExisted = null;
            if ((courseFileExisted = courseLectureDAO.getCourseFileLecture()) != null) {
                courseFileDAO.setId(courseFileExisted.getId());
            }

            courseLectureDAO.setCourseFileLecture(courseFileDAO);
            courseDAO = courseService.save(courseDAO);
            respCourseDTO = mapDAOToDTOCourses.map(courseDAO);
        }
        return respCourseDTO;
    }

    @SecuredEndPoint
    @PostMapping("/delete-course-lecture-file-by-lecture-id/{lectureId}")
    public List<CourseLectureDTO> deleteCourseLectureFile(HttpServletRequest request, HttpServletResponse response, @PathVariable Long lectureId, @RequestBody CourseDTO courseDTO) throws NotFoundEntityException {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        List<CourseLectureDTO> courseLectureDTOS = null;
        Optional<CourseDAO> optionalCourseDAO = courseService.findById(courseDTO.getId());
        optionalCourseDAO.orElseThrow(() -> new NotFoundEntityException(String.format("Course Not found with Id : %s", courseDTO.getId())));
        CourseDAO courseDAO = optionalCourseDAO.get();
        if (validationUtils.EntityRelatedToUser(courseDAO.getCreatedByTrainer().getAccountDAO().getUserDAO().getId(), userId)) {

            Optional<CourseLectureDAO> optionalCourseLectureDAO = courseDAO.getCourseLectures().stream().filter(lectureDAO -> lectureDAO.getId().equals(lectureId)).findFirst();
            optionalCourseLectureDAO.orElseThrow(() -> new NotFoundEntityException(String.format("CourseLecture Not found with Id : %s", lectureId)));
            CourseLectureDAO courseLectureDAO = optionalCourseLectureDAO.get();
            CourseFileDAO courseFileDAO = courseLectureDAO.getCourseFileLecture();
            courseLectureDAO.setCourseFileLecture(null);
            try {
                courseLectureService.save(courseLectureDAO);
            } catch (Exception ignored) {
                // WEIRD ISSUE: courseLectureService.save(courseLectureDAO) throw NPE BUT ONLY WITH THIS deleteCourseFile WORKS!
            }
            courseFileService.deleteCourseFile(courseFileDAO);
            courseLectureDTOS = mapDAOToDTOCourses.map(courseDAO.getCourseLectures());
        }
        return courseLectureDTOS;
    }


}
