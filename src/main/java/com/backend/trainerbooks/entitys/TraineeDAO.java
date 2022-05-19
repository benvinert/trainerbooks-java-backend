package com.backend.trainerbooks.entitys;


import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_ONLY;

@Data
@Cache(region = "trainees", usage = READ_ONLY)
@Cacheable
@Entity(name = "trainees")
public class TraineeDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String bodyGoal;
    private String bodyType;
    private String bodyFat;
    private Integer height;
    private Float weight;
    private String afterImage;
    private String beforeImage;
    private String profileImage;
    private String journey;
    private Long rankAccount;

    @OneToOne
    @Cache(usage= CacheConcurrencyStrategy.READ_ONLY, region = "trainers" )
    private TrainerDAO trainerDAO;

    @OneToOne
    @Cache(usage= CacheConcurrencyStrategy.READ_ONLY, region = "accounts" )
    private AccountDAO accountDAO;
}
