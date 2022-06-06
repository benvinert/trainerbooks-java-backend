package com.backend.trainerbooks.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ForumPostDTO {

    private Long id;

    private Long likes;
    private String postText;
    private ForumPostDTO quote;
    private ForumTopicDTO forumTopic;
}
