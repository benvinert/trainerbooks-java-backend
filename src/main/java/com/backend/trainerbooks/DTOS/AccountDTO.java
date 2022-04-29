package com.backend.trainerbooks.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    protected Long id;
    private String description;

//    private String location;
//    private ZonedDateTime dateOfBirthday;
    private ZonedDateTime createdDate;


}
