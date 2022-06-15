package com.backend.trainerbooks.entitys;

import com.backend.trainerbooks.enums.LikeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity(name = "likes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LikeDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private LikeEnum likeEnum;

    @OneToOne
    private UserDAO byUser;


}
