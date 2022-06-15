package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.ForumPostDAO;
import com.backend.trainerbooks.repositorys.ForumPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForumPostService {

    private final ForumPostRepository forumPostRepository;

    public ForumPostDAO save(ForumPostDAO forumPostDAO) {
        return forumPostRepository.save(forumPostDAO);
    }

    public Optional<ForumPostDAO> findByPostId(Long postId) {
        return forumPostRepository.findById(postId);
    }
}
