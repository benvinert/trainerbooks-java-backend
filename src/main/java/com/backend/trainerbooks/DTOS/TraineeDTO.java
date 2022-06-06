package com.backend.trainerbooks.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeDTO extends AccountDTO implements Serializable {

    private String fullName;
    private TrainerDTO trainerDTO;
    private String bodyGoal;
    private String bodyType;
    private String bodyFat;
    private Integer height;
    private Float weight;
    private String journey;
    private String afterImage;
    private String beforeImage;

}
