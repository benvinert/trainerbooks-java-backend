package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.TrainerDAO;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

@Component
public class NativeQueryRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    public List<TrainerDAO> getAllRankedTrainers() {
        String sQuery = "SELECT *, rank() over (order by (reviews_amount + clients_amount) DESC) FROM trainers";
        Query query = entityManager.createNativeQuery(sQuery,TrainerDAO.class);
        List<TrainerDAO> rankedTrainerResults = query.getResultList();
        Long rank = 1L;
        for (TrainerDAO t : rankedTrainerResults) {
            t.setRankAccount(rank);
            rank++;
        }

        return rankedTrainerResults;
    }
}
