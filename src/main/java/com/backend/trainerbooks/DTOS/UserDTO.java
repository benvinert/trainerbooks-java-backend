package com.backend.trainerbooks.DTOS;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    @JsonIgnore
    private Long id;
    @Size(min = 3 , max = 15, message = "username should be minimum with 3 characters and not greater than 15")
    private String username;
    private String password;

    @Email
    @NotBlank
    private String email;
    private Boolean isActive;
    private String roles;//ROLE_USER,ROLE_ADMIN
    private ZonedDateTime createdDate;
}
