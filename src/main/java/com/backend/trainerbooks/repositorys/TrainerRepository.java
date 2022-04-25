package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.TrainerDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<TrainerDAO,Long> {
}
