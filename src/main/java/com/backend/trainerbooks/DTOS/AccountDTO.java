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
    private String category;
    private Long rankAccount;
    private String profileImage;
    private ZonedDateTime createdDate;
    private Long UserId;


}
