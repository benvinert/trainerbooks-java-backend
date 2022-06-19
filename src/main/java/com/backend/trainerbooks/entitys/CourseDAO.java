package com.backend.trainerbooks.entitys;

import com.backend.trainerbooks.enums.CourseLevel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity(name="courses")
public class CourseDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private ZonedDateTime createdDate;

    private String title;
    private String description;
    private String language;
    private CourseLevel courseLevel;
    private String category;

    @OneToOne
    private CourseFileDAO courseImage;

    @OneToOne
    private CourseFileDAO promotionalVideo;

    private String whatUWillLearn;
    private String requirements;
    private Float price;
    private Boolean free;
    private String welcomeMessage;
    private String congratsMessage;

    @OneToMany(fetch = FetchType.LAZY)
    private List<UserDAO> students;

    @ManyToOne(fetch = FetchType.LAZY)
    private TrainerDAO createdBy;
}
