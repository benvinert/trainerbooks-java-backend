package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.ForumPostDAO;
import com.backend.trainerbooks.repositorys.ForumPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForumPostService {

    private final ForumPostRepository forumPostRepository;

    public ForumPostDAO save(ForumPostDAO forumPostDAO) {
        return forumPostRepository.save(forumPostDAO);
    }
}
