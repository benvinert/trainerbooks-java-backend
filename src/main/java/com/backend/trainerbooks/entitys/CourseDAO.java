package com.backend.trainerbooks.entitys;

import com.backend.trainerbooks.enums.CourseLevel;
import com.backend.trainerbooks.enums.CourseStatus;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity(name="courses")
@Table(indexes = {@Index(name = "title_index_unique", columnList = "title", unique = true) })
public class CourseDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private ZonedDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private TrainerDAO createdByTrainer;

    @OneToMany(fetch = FetchType.LAZY)
    private List<UserDAO> students;

    private Float price;
    private Boolean free;

    private String title;
    private String description;
    private String language;
    private String category;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id",referencedColumnName = "id")
    private List<CourseLectureDAO> courseLectures;

    private CourseLevel courseLevel;

    private CourseStatus courseStatus;

    @OneToOne
    private CourseFileDAO courseImage;
    @OneToOne
    private CourseFileDAO promotionalVideo;

    private String whatUWillLearn;
    private String requirements;
    private String welcomeMessage;
    private String congratsMessage;
}
