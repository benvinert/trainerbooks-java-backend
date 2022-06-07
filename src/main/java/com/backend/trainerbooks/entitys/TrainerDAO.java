package com.backend.trainerbooks.entitys;

import lombok.Data;
import org.hibernate.annotations.Cache;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

@Data
@Cache(region = "trainers", usage = READ_WRITE)
@Cacheable
@Entity(name = "trainers")
public class TrainerDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String whoIs;
    private String education;//Add It in profile , manually by customer.
    private String aboutProcess;
    private String moreAbout;
    private String city;
    private String beforeImage;
    private String afterImage;
    private String profileImage;
    private String category;
    private Long rankAccount;
    private Long reviewsAmount;
    private Long clientsAmount;

    @OneToMany(cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    @Cache(usage= READ_WRITE, region = "trainees" )
    private List<TraineeDAO> traineeTransformations;

    @OneToMany(fetch= FetchType.LAZY)
    @Cache(usage= READ_WRITE, region = "trainees" )
    private List<TraineeDAO> trainees;

    @OneToOne(cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    @Cache(usage= READ_WRITE, region = "accounts" )
    private AccountDAO accountDAO;



}
