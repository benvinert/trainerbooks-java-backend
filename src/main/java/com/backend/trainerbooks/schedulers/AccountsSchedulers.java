package com.backend.trainerbooks.schedulers;

import com.backend.trainerbooks.services.NativeQueryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class AccountsSchedulers {
    Logger logger = LoggerFactory.getLogger(AccountsSchedulers.class);

    private final NativeQueryService nativeQueryService;

    @Scheduled(cron="0 0/2 * * * ?")
    public void updateRanks() {
        logger.info("Start Ranking Trainers");
        nativeQueryService.updateTrainerRanks();
        logger.info("Done Ranking Trainers");
    }
}
