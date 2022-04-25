package com.backend.trainerbooks.utils;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;


@RequiredArgsConstructor
@Component
public class SecurityUtils {

    private final BCryptPasswordEncoder passwordEncoder;

    @Named("passwordEncoder")
    public String passwordEncoder(String userPassword) {
        return StringUtils.isEmpty(userPassword) ? userPassword : passwordEncoder.encode(userPassword);
    }
}
