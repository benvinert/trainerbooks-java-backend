package com.backend.trainerbooks.entitys;

import com.backend.trainerbooks.entitys.interfaces.Likeable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "forum_topics")
public class ForumTopicDAO implements Likeable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;
    private ZonedDateTime createdDate;

    @OneToOne
    private UserDAO byUser;
    private Long numOfPosts;
    private String content;

    @OneToMany(cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    @JoinColumn(name = "topic_id_dao" , referencedColumnName = "id")
    private List<ForumPostDAO> posts;

    private Long categoryId;
    private String tags;

    @OneToOne(cascade = CascadeType.ALL)
    private ForumPostDAO lastPost;
    private Long likesCounter;
    @ManyToMany(cascade = CascadeType.ALL,fetch= FetchType.LAZY)
    private List<LikeDAO> usersLikes;

    @OneToMany(cascade = CascadeType.ALL,fetch= FetchType.LAZY)
    @JoinColumn(name = "topic_id" , referencedColumnName = "id")
    private List<ForumFileDAO> topicFiles;
}
