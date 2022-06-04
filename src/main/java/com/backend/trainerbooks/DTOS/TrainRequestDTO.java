package com.backend.trainerbooks.DTOS;

import com.backend.trainerbooks.enums.StatusTrainRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class TrainRequestDTO {

    private Long id;
    private TraineeDTO traineeAccount;
    private TrainerDTO trainerAccount;
    private Long trainerUserId;
    private ZonedDateTime createdDate;
    private StatusTrainRequest status;
}
