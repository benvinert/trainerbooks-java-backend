package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.TrainRequestDAO;
import com.backend.trainerbooks.enums.StatusTrainRequest;
import com.backend.trainerbooks.repositorys.TrainRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TrainRequestService {

    private final TrainRequestRepository trainRequestRepository;

    public TrainRequestDAO save(TrainRequestDAO trainRequestDAO) {
        return trainRequestRepository.save(trainRequestDAO);
    }

    public List<TrainRequestDAO> getAllPendingTrainRequestsByUid(Long uid) {
        return trainRequestRepository.findAllByTrainerUserIdAndStatus(uid, StatusTrainRequest.PENDING);
    }

    public Optional<TrainRequestDAO> findById(Long trainRequestId) {
        return trainRequestRepository.findById(trainRequestId);
    }
}
