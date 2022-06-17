package com.backend.trainerbooks.utils;

import com.backend.trainerbooks.DTOS.ForumTopicDTO;
import com.backend.trainerbooks.enums.ForumCategoryEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class ForumUtils {
    private final ValidationUtils validationUtils;


    public boolean addTopicValidation(ForumTopicDTO forumTopicDTO) {
        boolean isValidCategory = Arrays.stream(ForumCategoryEnum.values())
                .anyMatch(eachEnum -> eachEnum.getValue() == forumTopicDTO.getCategoryId());

        return !validationUtils.isContainsURL(forumTopicDTO.getTitle()) &&
                !validationUtils.isContainsURL(forumTopicDTO.getContent()) &&
                validationUtils.isWithoutSpecialCharacters(forumTopicDTO.getTitle()) &&
                isValidCategory;
    }
}
