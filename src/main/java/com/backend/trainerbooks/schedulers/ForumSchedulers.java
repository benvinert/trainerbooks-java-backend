package com.backend.trainerbooks.schedulers;

import com.backend.trainerbooks.services.NativeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class ForumSchedulers {

    private final NativeQueryService nativeQueryService;

    @Scheduled(cron = "0 0/3 * * * ?")
    private void updateTopicsScheduler() {
        nativeQueryService.initAllHotTopics();
    }


}
