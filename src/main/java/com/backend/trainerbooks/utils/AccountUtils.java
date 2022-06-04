package com.backend.trainerbooks.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AccountUtils {

    public boolean fieldHasLengthMoreThan(String fieldValue,int length) {
        return StringUtils.hasText(fieldValue) && fieldValue.length() > length;
    }
}
