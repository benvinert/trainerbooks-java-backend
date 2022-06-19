package com.backend.trainerbooks.entitys;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity(name = "course_video")
public class CourseLectureDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String length;
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    private List<CourseFileDAO> courseFileDAO;

    @OneToMany(fetch = FetchType.LAZY)
    private List<CourseFileDAO> resources;

}
