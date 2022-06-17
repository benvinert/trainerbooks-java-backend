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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "forum_posts")
public class ForumPostDAO implements Likeable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String postText;
    private ZonedDateTime createdDate;

    @OneToOne
    private UserDAO byUser;
    @OneToOne
    private ForumPostDAO quotePost;
    private Long topicId;
    private Long likesCounter;
    @ManyToMany(cascade = CascadeType.ALL,fetch= FetchType.LAZY)
    private List<LikeDAO> usersLikes;

    @OneToMany(cascade = CascadeType.ALL,fetch= FetchType.LAZY)
    private List<ForumFileDAO> postFiles;


}
