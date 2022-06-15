package com.backend.trainerbooks.DTOS;

import com.backend.trainerbooks.entitys.LikeDAO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ForumPostDTO {

    private Long id;
    private UserDTO byUser;
    private Long likes;
    @NotBlank
    private String postText;
    private ForumPostDTO quotePost;
    private ZonedDateTime createdDate;
    private Long topicId;

    private Long likesCounter;
    private List<LikeDAO> usersLikes;
}
