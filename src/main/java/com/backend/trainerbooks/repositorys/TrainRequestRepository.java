package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.TrainRequestDAO;
import com.backend.trainerbooks.enums.StatusTrainRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainRequestRepository extends JpaRepository<TrainRequestDAO,Long> {
    List<TrainRequestDAO> findAllByTrainerUserIdAndStatus(Long trainerUid, StatusTrainRequest statusTrainRequest);
}
