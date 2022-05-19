package com.backend.trainerbooks.DTOS;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private Long id;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String country;
    private String city;
    private ZonedDateTime birthdate;
    private boolean loggedIn;

    @Email
    @NotBlank
    private String email;
    private Boolean isActive;
    private String roles;//ROLE_USER,ROLE_ADMIN
    private ZonedDateTime createdDate;
}
