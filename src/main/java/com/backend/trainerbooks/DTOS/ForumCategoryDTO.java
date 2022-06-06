package com.backend.trainerbooks.DTOS;

import com.backend.trainerbooks.entitys.ForumTopicDAO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForumCategoryDTO {

    private Long id;
    private String name;
    private String description;
    private String categoryImageURL;
    private Long topics;
    private Long posts;

    private ForumTopicDAO lastTopic;
}
