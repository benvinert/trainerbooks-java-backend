package com.backend.trainerbooks.services;

import com.backend.trainerbooks.DTOS.ForumTopicDTO;
import com.backend.trainerbooks.entitys.TrainerDAO;
import com.backend.trainerbooks.enums.ForumCategoryEnum;
import com.backend.trainerbooks.repositorys.NativeQueryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class NativeQueryService {
    Logger logger = LoggerFactory.getLogger(NativeQueryService.class);

    private final NativeQueryRepository nativeQueryRepository;
    private final TrainerAccountService trainerAccountService;
    private Map<Integer, List<ForumTopicDTO>> hotTopicsByCategory;

    public void updateTrainerRanks() {
        List<TrainerDAO> trainerDAOS = nativeQueryRepository.getAllRankedTrainers();
        saveTrainerRanks(trainerDAOS);
    }

    public Map<Integer, List<ForumTopicDTO>> getHotTopicsByCategory() {
        return hotTopicsByCategory;
    }

    private void saveTrainerRanks(List<TrainerDAO> trainerDAOS) {
        trainerAccountService.saveAll(trainerDAOS);
    }


    private void updateHotTopicsMap() {
        List<ForumTopicDTO> forumTopicDTOS = nativeQueryRepository.getHotTopics();

        for (ForumTopicDTO eachTopicDTO : forumTopicDTOS) {
            List<ForumTopicDTO> forumTopicDTOSList = hotTopicsByCategory.get(eachTopicDTO.getCategoryId().intValue());
            if(forumTopicDTOSList != null) {
                forumTopicDTOSList.add(eachTopicDTO);
            }
        }
        logger.info("HOT TOPICS UPDATED.");
    }

    private void initHotTopicsMap() {
        this.hotTopicsByCategory = Map.of(ForumCategoryEnum.FOOD_AND_NUTRITION.getValue(),new LinkedList<>(),
                      ForumCategoryEnum.RECIPES.getValue(),new LinkedList<>(),
                      ForumCategoryEnum.FITNESS_AND_EXERCISES.getValue(),new LinkedList<>(),
                      ForumCategoryEnum.GETTING_STARTED.getValue(),new LinkedList<>(),
                      ForumCategoryEnum.CHALLENGES.getValue(),new LinkedList<>(),
                      ForumCategoryEnum.MOTIVATION.getValue(),new LinkedList<>());
    }

    @PostConstruct
    public void initAllHotTopics() {
        initHotTopicsMap();
        updateHotTopicsMap();
    }


    @Scheduled(cron = "0 0/3 * * * ?")
    private void updateTopicsScheduler() {
        initAllHotTopics();
    }
}
