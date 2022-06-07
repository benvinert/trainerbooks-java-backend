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
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "forum_posts")
public class ForumPostDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long likes;
    private String postText;

    @OneToOne
    private UserDAO byUser;

    @OneToOne
    private ForumPostDAO quote;

    @ManyToOne(cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    private ForumTopicDAO forumTopic;


}
