package com.backend.trainerbooks.DTOS;

import com.backend.trainerbooks.entitys.ForumCategoryDAO;
import com.backend.trainerbooks.entitys.ForumPostDAO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.OneToOne;
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
    private Long numOfPosts;
    @NotEmpty
    private List<ForumPostDTO> posts;
    @NotNull
    private ForumCategoryDTO forumCategory;

    @NotBlank
    private String subCategory;

    @OneToOne
    private ForumPostDTO lastPost;
}
