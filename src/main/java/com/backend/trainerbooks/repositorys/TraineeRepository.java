package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.TraineeDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraineeRepository extends JpaRepository<TraineeDAO,Long> {
}
