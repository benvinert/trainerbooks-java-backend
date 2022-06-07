package com.backend.trainerbooks.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidationUtils {
    public static final String regex = "((http|https)://)(www.)?"
            + "[a-zA-Z0-9@:%._\\+~#?&//=]"
            + "{2,256}\\.[a-z]"
            + "{2,6}\\b([-a-zA-Z0-9@:%"
            + "._\\+~#?&//=]*)";

    public boolean isContainsURL(String url) {
        Pattern p = Pattern.compile(regex);
        if (url == null) {
            return true;
        }

        Matcher m = p.matcher(url);
        return m.matches();
    }
}
