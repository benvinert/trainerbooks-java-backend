package com.backend.trainerbooks.DTOS;

import com.backend.trainerbooks.entitys.CourseFileDAO;
import com.backend.trainerbooks.entitys.CourseLectureDAO;
import com.backend.trainerbooks.entitys.TrainerDAO;
import com.backend.trainerbooks.enums.CourseLevel;
import com.backend.trainerbooks.enums.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private Long id;
    private ZonedDateTime createdDate;

    private TrainerDTO createdByTrainer;
    private List<UserDTO> students;

    private Float price;
    private Boolean free;

    private List<CourseLectureDTO> courseLectures;

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String language;
    @NotBlank
    private String category;
    @NotBlank
    private CourseLevel courseLevel;

    private CourseStatus courseStatus;
    private CourseFileDTO courseImage;
    private CourseFileDTO promotionalVideo;

    private String whatUWillLearn;
    private String requirements;
    private String welcomeMessage;
    private String congratsMessage;



}
