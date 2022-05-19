package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.TrainerDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

import static org.hibernate.jpa.QueryHints.HINT_CACHEABLE;

public interface TrainerRepository extends JpaRepository<TrainerDAO,Long> {
    List<TrainerDAO> findAllByAccountDAO_UserDAO_Id(Long userId);
    TrainerDAO findTrainerDAOById(Long trainerAccountId);

    @QueryHints(@QueryHint(name=HINT_CACHEABLE ,value="true"))
    List<TrainerDAO> findAllByOrderByRankAccountAsc();

}
