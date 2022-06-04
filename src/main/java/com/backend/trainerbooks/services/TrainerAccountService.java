package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.TrainerDAO;
import com.backend.trainerbooks.repositorys.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerAccountService {

    private final TrainerRepository trainerRepository;

    public TrainerDAO save(TrainerDAO trainerDAO) {
        return trainerRepository.save(trainerDAO);
    }

    public List<TrainerDAO> saveAll(List<TrainerDAO> trainerDAOs) {
        return trainerRepository.saveAll(trainerDAOs);
    }

    public List<TrainerDAO> findAllTrainerAccountsByUserId(Long userId) {
        return trainerRepository.findAllByAccountDAO_UserDAO_Id(userId);
    }

    public List<TrainerDAO> findAll() {
        return trainerRepository.findAll();
    }

    public List<TrainerDAO> findAllTrainerAccountsByRank() {
        return trainerRepository.findAllByOrderByRankAccountAsc();
    }

    public TrainerDAO findTrainerAccountByTrainerId(Long accountId) {
        return trainerRepository.findTrainerDAOById(accountId);
    }
}
