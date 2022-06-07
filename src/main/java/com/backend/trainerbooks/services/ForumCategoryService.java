package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.ForumCategoryDAO;
import com.backend.trainerbooks.repositorys.ForumCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForumCategoryService {
    private final ForumCategoryRepository forumCategoryRepository;

    public List<ForumCategoryDAO> findAllCategories() {
        return forumCategoryRepository.findAll();
    }

    public Optional<ForumCategoryDAO> findById(Long id) {
        return forumCategoryRepository.findById(id);
    }

    public ForumCategoryDAO findByUrl(String url) {
        return forumCategoryRepository.findByUrl(url);
    }

    public ForumCategoryDAO save(ForumCategoryDAO forumCategoryDAO) {
        return forumCategoryRepository.save(forumCategoryDAO);
    }

}
