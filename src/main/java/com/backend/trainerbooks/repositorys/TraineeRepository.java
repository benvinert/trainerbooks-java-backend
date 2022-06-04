package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.TraineeDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TraineeRepository extends JpaRepository<TraineeDAO,Long> {
    List<TraineeDAO> findAllByAccountDAO_UserDAO_Id(Long userId);
    TraineeDAO findFirstByAccountDAO_UserDAO_IdAndAccountDAO_Category(Long userId,String category);


}
