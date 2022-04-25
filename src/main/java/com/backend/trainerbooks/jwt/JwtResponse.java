package com.backend.trainerbooks.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class JwtResponse {
    private String jwtToken;
}
