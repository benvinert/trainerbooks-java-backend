package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.TrainerDAO;
import com.backend.trainerbooks.repositorys.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerAccountService {

    @Autowired
    private TrainerRepository trainerRepository;

    public TrainerDAO save(TrainerDAO trainerDAO) {
        return trainerRepository.save(trainerDAO);
    }
}
