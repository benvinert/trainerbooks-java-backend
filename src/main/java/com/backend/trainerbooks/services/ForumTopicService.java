package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.ForumTopicDAO;
import com.backend.trainerbooks.repositorys.ForumTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForumTopicService {

    private final ForumTopicRepository forumTopicRepository;

    public List<ForumTopicDAO> findAllForumTopicsByCategoryIdAndOrderLastPostCreatedDate(Long id,Pageable pageable) {
        return forumTopicRepository.findAllByCategoryIdOrderByLastPostCreatedDateDesc(id,pageable);
    }

    public List<ForumTopicDAO> findAllForumTopicsByCategoryIdAndTagContainsOrderByDate(Long id,String tag) {
        return forumTopicRepository.findAllTopicsByCategoryIdAndTagOrderByLastPostCreatedDateDesc(id,"%" + tag + "%");
    }

    public ForumTopicDAO save(ForumTopicDAO forumTopicDAO) {
        return forumTopicRepository.save(forumTopicDAO);
    }

    public Optional<ForumTopicDAO> findByTopicId(Long id) {
        return forumTopicRepository.findById(id);
    }
}
