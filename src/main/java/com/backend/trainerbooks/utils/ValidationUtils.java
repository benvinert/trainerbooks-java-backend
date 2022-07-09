package com.backend.trainerbooks.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidationUtils {
    public static final String regex = "(?!\\S+youtube\\.com)((?<!\\S)(((f|ht){1}tp[s]?:\\/\\/|(?<!\\S)www\\.)[-a-zA-Z0-9@:%_\\+.~#?&\\/\\/=]+))\n";
    public static final String regexContainsSpecialCharacters = "[^&%$#@!~]*";
    public boolean isContainsURL(String url) {
        Pattern p = Pattern.compile(regex);
        if (url == null) {
            return true;
        }

        Matcher m = p.matcher(url);
        return m.matches();
    }

    public boolean isWithoutSpecialCharacters(String text) {
        Pattern p = Pattern.compile(regexContainsSpecialCharacters);
        if (text == null) {
            return false;
        }

        Matcher m = p.matcher(text);
        return m.matches();
    }

    public boolean EntityRelatedToUser(Long entityId,Long userTokenId ) {
        return entityId.equals(userTokenId);
    }


}
