package com.backend.trainerbooks.DTOS;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseLectureDTO {

    private Long id;

    private String title;
    private String length;
    private String description;
    private CourseFileDTO courseFileLecture;
    private List<CourseFileDTO> resources;
}
