package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.TraineeDAO;
import com.backend.trainerbooks.repositorys.TraineeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TraineeAccountService {

    private final TraineeRepository traineeRepository;


    public TraineeDAO save(TraineeDAO traineeDAO){
        return traineeRepository.save(traineeDAO);
    }
}
