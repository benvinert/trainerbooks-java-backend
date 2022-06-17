package com.backend.trainerbooks.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ForumFileDTO {

    private Long id;

    private String fileName;
    private ZonedDateTime createdDate;
    private Long size;
    private String type;
}
