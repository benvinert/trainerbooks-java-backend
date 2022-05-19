package com.backend.trainerbooks.services;

import com.backend.trainerbooks.controllers.UserControllers;
import com.backend.trainerbooks.entitys.TrainerDAO;
import com.backend.trainerbooks.repositorys.NativeQueryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NativeQueryService {

    private final NativeQueryRepository nativeQueryRepository;
    private final TrainerAccountService trainerAccountService;

    public List<TrainerDAO> updateTrainerRanks() {
        List<TrainerDAO> trainerDAOS = nativeQueryRepository.getAllRankedTrainers();
        saveTrainerRanks(trainerDAOS);
        return trainerDAOS;
    }

    private void saveTrainerRanks(List<TrainerDAO> trainerDAOS) {
        trainerAccountService.saveAll(trainerDAOS);

    }
}
