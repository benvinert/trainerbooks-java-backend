package com.backend.trainerbooks.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOAuthDTO extends UserDTO {
    private String name;
    private String image;
}
