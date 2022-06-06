package com.backend.trainerbooks.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeTransformationDTO {
    List<TraineeDTO> traineeDTOS;
    TrainerDTO trainerDTO;
}
