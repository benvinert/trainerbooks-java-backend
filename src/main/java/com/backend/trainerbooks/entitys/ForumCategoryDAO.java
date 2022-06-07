package com.backend.trainerbooks.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "forum_categories")
public class ForumCategoryDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String categoryImageURL;
    private Long topics;
    private Long posts;
    private String url;

    @OneToOne
    private ForumTopicDAO lastTopic;
}
