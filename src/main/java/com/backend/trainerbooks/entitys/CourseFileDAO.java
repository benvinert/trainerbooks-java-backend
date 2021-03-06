package com.backend.trainerbooks.entitys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Data
@Entity(name = "course_file")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseFileDAO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private ZonedDateTime createdDate;
    private Long size;
    private String type;
}

