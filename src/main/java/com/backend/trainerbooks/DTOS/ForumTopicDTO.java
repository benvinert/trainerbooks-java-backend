package com.backend.trainerbooks.DTOS;

import com.backend.trainerbooks.entitys.ForumCategoryDAO;
import com.backend.trainerbooks.entitys.ForumPostDAO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.OneToOne;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ForumTopicDTO {

    private Long id;

    private String title;
    private ZonedDateTime createdDate;
    private ForumCategoryDTO forumCategory;
    private Long posts;

    @OneToOne
    private ForumPostDTO lastPost;
}
