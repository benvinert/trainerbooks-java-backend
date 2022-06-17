package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.DTOS.ForumTopicDTO;
import com.backend.trainerbooks.entitys.ForumTopicDAO;
import com.backend.trainerbooks.entitys.TrainerDAO;
import com.backend.trainerbooks.mappers.DAOToDTO.IMapDAOToDTOForum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

@Component
@Transactional
public class NativeQueryRepository {
    private static final String GET_HOT_TOPICS_FROM_CATEGORIES = "select * from (select forum_topics.*, row_number() over (partition by category_id order by num_of_posts desc) as seqnum from forum_topics) forum_topics where seqnum <= 4;";

    @PersistenceContext
    protected EntityManager entityManager;

    private final IMapDAOToDTOForum mapDAOToDTOForum;


    @Autowired
    public NativeQueryRepository(IMapDAOToDTOForum mapDAOToDTOForum) {
        this.mapDAOToDTOForum = mapDAOToDTOForum;
    }




    public List<TrainerDAO> getAllRankedTrainers() {
        String sQuery = "SELECT *, rank() over (order by (reviews_amount + clients_amount) DESC) FROM trainers";
        Query query = entityManager.createNativeQuery(sQuery, TrainerDAO.class);
        List<TrainerDAO> rankedTrainerResults = query.getResultList();
        Long rank = 1L;
        for (TrainerDAO t : rankedTrainerResults) {
            t.setRankAccount(rank);
            rank++;
        }

        return rankedTrainerResults;
    }

    public List<ForumTopicDTO> getHotTopics() {
        Query query = entityManager.createNativeQuery(GET_HOT_TOPICS_FROM_CATEGORIES, ForumTopicDAO.class);
        List<ForumTopicDAO> forumTopicDAOS = query.getResultList();
        List<ForumTopicDAO> fullLoadedObjects = new LinkedList<>();
        for (ForumTopicDAO forumTopicDAO : forumTopicDAOS) {
            fullLoadedObjects.add(entityManager.getReference(ForumTopicDAO.class,forumTopicDAO.getId()));// Load whole Entity(Without lazy loading)
        }
        return mapDAOToDTOForum.mapDAOToDTOTopic(fullLoadedObjects);
    }
}
