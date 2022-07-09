package com.backend.trainerbooks.DTOS;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseFileDTO {
    private Long id;

    private String fileName;
    private ZonedDateTime createdDate;
    private Long size;
    private String type;
}
