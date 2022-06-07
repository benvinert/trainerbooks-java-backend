package com.backend.trainerbooks.DTOS;

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
    private String url;
    private ForumTopicDTO lastTopic;
}
