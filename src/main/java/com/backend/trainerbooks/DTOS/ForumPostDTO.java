package com.backend.trainerbooks.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ForumPostDTO {

    private Long id;

    private Long likes;
    private String postText;
    private Long postQuoteId;
    private ZonedDateTime createdDate;
}
