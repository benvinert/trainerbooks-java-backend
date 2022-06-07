package com.backend.trainerbooks.entitys;

import com.backend.trainerbooks.DTOS.UserDTO;
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
import java.util.List;

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

    @OneToOne
    private UserDAO byUser;
    private Long numOfPosts;

    @OneToMany(cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    private List<ForumPostDAO> posts;

    @ManyToOne(fetch= FetchType.LAZY)
    private ForumCategoryDAO forumCategory;
    private String subCategory;

    @OneToOne
    private ForumPostDAO lastPost;
}
