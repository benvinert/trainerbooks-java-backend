package com.backend.trainerbooks.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeDTO extends AccountDTO{

    private TrainerDTO trainerDTO;
}
