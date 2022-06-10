package com.backend.trainerbooks.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ForumPostDTO {

    private Long id;
    private UserDTO byUser;
    private Long likes;
    @NotBlank
    private String postText;
    private Long postQuoteId;
    private ZonedDateTime createdDate;
    private Long topicId;
}
