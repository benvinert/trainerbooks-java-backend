package com.backend.trainerbooks.entitys;

import com.backend.trainerbooks.DTOS.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "forum_topics")
public class ForumTopicDAO {

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
    private List<ForumPostDAO> posts;

    private Long categoryId;
    private String tags;

    @OneToOne
    private ForumPostDAO lastPost;
    private Long likes;

}
