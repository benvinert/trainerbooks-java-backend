package com.backend.trainerbooks.entitys;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity(name = "trainers")
public class TrainerDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String whoIs;
    private String education;
    private String aboutProcess;
    private String moreAbout;
    private String city;

    @ManyToOne
    private AccountDAO accountDAO;



}
