package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.ForumCategoryDAO;
import com.backend.trainerbooks.entitys.ForumTopicDAO;
import com.backend.trainerbooks.repositorys.ForumCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumCategoryService {
    private final ForumCategoryRepository forumCategoryRepository;

    public List<ForumCategoryDAO> findAllCategories() {
        return forumCategoryRepository.findAll();
    }

}
