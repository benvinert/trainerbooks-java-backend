package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.ForumPostDAO;
import com.backend.trainerbooks.entitys.ForumTopicDAO;
import com.backend.trainerbooks.repositorys.ForumPostRepository;
import com.backend.trainerbooks.repositorys.ForumTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumTopicService {

    private final ForumTopicRepository forumTopicRepository;

    public List<ForumTopicDAO> findAllForumTopicsByCategoryIdAndOrderByDate(Long id) {
        return forumTopicRepository.findAllByCategoryIdOrderByCreatedDateDesc(id);
    }

    public ForumTopicDAO save(ForumTopicDAO forumTopicDAO) {
        return forumTopicRepository.save(forumTopicDAO);
    }
}
