package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.TraineeDAO;
import com.backend.trainerbooks.entitys.TrainerDAO;
import com.backend.trainerbooks.repositorys.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerAccountService {

    @Autowired
    private TrainerRepository trainerRepository;

    public TrainerDAO save(TrainerDAO trainerDAO) {
        return trainerRepository.save(trainerDAO);
    }

    public List<TrainerDAO> findAllTrainerAccountsByUserId(Long userId) {
        return trainerRepository.findAllByAccountDAO_Id(userId);
    }
}
