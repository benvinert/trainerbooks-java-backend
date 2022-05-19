package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.TraineeDAO;
import com.backend.trainerbooks.repositorys.TraineeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TraineeAccountService {

    private final TraineeRepository traineeRepository;


    public TraineeDAO save(TraineeDAO traineeDAO){
        return traineeRepository.save(traineeDAO);
    }

    public List<TraineeDAO> findAllTraineeAccountsByUserId(Long userId) {
        return traineeRepository.findAllByAccountDAO_UserDAO_Id(userId);
    }

    public Optional<TraineeDAO> findByTraineeId(Long traineeId) {
        return traineeRepository.findById(traineeId);
    }
}
