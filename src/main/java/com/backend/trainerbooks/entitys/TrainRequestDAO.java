package com.backend.trainerbooks.entitys;

import com.backend.trainerbooks.enums.StatusTrainRequest;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;

@Data
@Entity(name = "trainRequests")
public class TrainRequestDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private TraineeDAO traineeAccount;
    @OneToOne
    private TrainerDAO trainerAccount;
    private Long trainerUserId;
    private ZonedDateTime createdDate;
    private StatusTrainRequest status;
}
