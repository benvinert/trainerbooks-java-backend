package com.backend.trainerbooks.entitys;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity(name = "trainees")
public class TraineeDAO {
    @Id
    private Long id;

    @ManyToOne
    private AccountDAO accountDAO;
}
