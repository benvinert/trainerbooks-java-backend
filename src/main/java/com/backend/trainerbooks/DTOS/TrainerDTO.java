package com.backend.trainerbooks.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerDTO extends AccountDTO{
    private String fullName;
    private String aboutProcess;
    private String moreAbout;
    private String city;
    private String beforeImage;
    private String afterImage;
    private String whoIs;
    private Long reviewsAmount;
    private Long clientsAmount;

    private List<TraineeDTO> traineeDTOList;

}
