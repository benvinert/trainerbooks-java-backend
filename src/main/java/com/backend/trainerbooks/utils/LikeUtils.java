package com.backend.trainerbooks.utils;

import com.backend.trainerbooks.entitys.ForumTopicDAO;
import com.backend.trainerbooks.entitys.LikeDAO;
import com.backend.trainerbooks.entitys.UserDAO;
import com.backend.trainerbooks.entitys.interfaces.Likeable;
import com.backend.trainerbooks.enums.LikeEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@Component
public class LikeUtils {
    public Long getCounterResultsByLikeEnum(Long currentCounter, LikeEnum userLikeEnumClick) {
        Long counterResult = null;
        switch (userLikeEnumClick) {
            case UP:
                counterResult = currentCounter + 1L;
                break;
            case DOWN:
                counterResult = currentCounter - 1L;
                break;
            default:
                counterResult = currentCounter;
        }
        return counterResult;
    }

    public Long getRevertedCounterResultsByLikeEnum(Long currentCounter, LikeEnum userLikeEnumClick) {
        Long counterResult = null;
        switch (userLikeEnumClick) {
            case UP:
                counterResult = currentCounter - 1L;
                break;
            case DOWN:
                counterResult = currentCounter + 1L;
                break;
            default:
                counterResult = currentCounter;
        }
        return counterResult;
    }

    public Optional<LikeDAO> getUserLikeIfContains(List<LikeDAO> likeDAOS,Long userId) {
        Optional<LikeDAO> userLikeDAO;
        if(likeDAOS.size() > 40) {
            userLikeDAO = likeDAOS.parallelStream().findFirst().filter(eachLikeDAO -> Objects.equals(eachLikeDAO.getByUser().getId(), userId));
        } else {
            userLikeDAO = likeDAOS.stream().findFirst().filter(eachLikeDAO -> Objects.equals(eachLikeDAO.getByUser().getId(), userId));
        }
        return userLikeDAO;
    }

    public void updateUserLikeAndCounter(Likeable likeableDAO, LikeDAO userLike, LikeEnum userLikeEnumClick,AtomicReference<Long> newCounterResult, List<LikeDAO> likeDAOS) {
        newCounterResult.set(getRevertedCounterResultsByLikeEnum(likeableDAO.getLikesCounter(), userLike.getLikeEnum()));
        if(userLike.getLikeEnum().equals(userLikeEnumClick)) {
            likeDAOS.remove(userLike);
        } else {
            newCounterResult.set(getCounterResultsByLikeEnum(newCounterResult.get(), userLikeEnumClick));
            userLike.setLikeEnum(userLikeEnumClick);
        }
    }

    public void createNewLikeAndUpdateUserLikeAndCounter(Likeable likeableDAO,LikeEnum userLikeEnumClick, AtomicReference<Long> newCounterResult,List<LikeDAO> likeDAOS,UserDAO userDAO) {
        LikeDAO likeDAO = LikeDAO.builder().likeEnum(userLikeEnumClick).byUser(userDAO).build();
        likeDAOS.add(likeDAO);
        newCounterResult.set(getCounterResultsByLikeEnum(likeableDAO.getLikesCounter(), userLikeEnumClick));
    }
}
