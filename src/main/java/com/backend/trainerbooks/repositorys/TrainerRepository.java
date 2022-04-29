package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.TrainerDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerRepository extends JpaRepository<TrainerDAO,Long> {
    List<TrainerDAO> findAllByAccountDAO_Id(Long userId);
}
