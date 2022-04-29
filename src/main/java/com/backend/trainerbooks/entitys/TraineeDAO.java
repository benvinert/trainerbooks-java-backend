package com.backend.trainerbooks.entitys;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
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

    @ManyToOne
    private AccountDAO accountDAO;
}
