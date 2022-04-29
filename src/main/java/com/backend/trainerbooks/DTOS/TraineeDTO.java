package com.backend.trainerbooks.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeDTO extends AccountDTO{

    private TrainerDTO trainerDTO;
    @NotBlank
    private String bodyGoal;
    @NotBlank
    private String bodyType;
    @NotBlank
    private String bodyFat;
    @NotNull
    private Integer height;
    @NotNull
    private Float weight;
}
