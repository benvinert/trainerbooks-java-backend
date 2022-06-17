package com.backend.trainerbooks.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ForumTopicDTO {

    private Long id;

    @NotBlank
    private String title;
    private ZonedDateTime createdDate;

    private UserDTO byUser;
    private String content;
    private Long numOfPosts;
    private List<ForumPostDTO> posts;
    @NotNull
    private Long categoryId;

    @NotEmpty
    private List<String> tags;

    private ForumPostDTO lastPost;
    private Long likesCounter;
    private List<LikeDTO> usersLikes;
    private List<ForumFileDTO> topicFiles;
}
