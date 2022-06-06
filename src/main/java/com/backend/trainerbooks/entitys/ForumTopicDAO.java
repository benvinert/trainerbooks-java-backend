package com.backend.trainerbooks.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "forum_topics")
public class ForumTopicDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;
    private ZonedDateTime createdDate;
    @ManyToOne(cascade = CascadeType.ALL, fetch= FetchType.LAZY)
//    @Cache(usage= READ_WRITE, region = "users" )
    private ForumCategoryDAO forumCategory;
    private Long posts;

    @OneToOne
    private ForumPostDAO lastPost;
}
