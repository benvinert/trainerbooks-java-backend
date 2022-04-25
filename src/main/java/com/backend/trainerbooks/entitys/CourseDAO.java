package com.backend.trainerbooks.entitys;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;

@Data
@Entity(name="courses")
public class CourseDAO {

    @Id
    private Long id;
    private ZonedDateTime createdDate;

    @ManyToOne
    private TrainerDAO trainerDAO;
}
