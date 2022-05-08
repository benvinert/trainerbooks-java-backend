package com.backend.trainerbooks.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private boolean error;
    private String jwtToken;
    private String message;
}
