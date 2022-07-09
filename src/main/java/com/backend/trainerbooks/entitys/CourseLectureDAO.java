package com.backend.trainerbooks.entitys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Data
@Entity(name = "course_lecture")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseLectureDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String length;
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch= FetchType.EAGER,orphanRemoval = true)
    private CourseFileDAO courseFileLecture;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_resource_id" , referencedColumnName = "id")
    private List<CourseFileDAO> resources;

}
