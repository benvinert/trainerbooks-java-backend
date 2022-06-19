package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.ForumPostDAO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumPostRepository extends JpaRepository<ForumPostDAO,Long> {
    List<ForumPostDAO> findAllByTopicIdOrderByCreatedDateDesc(Long topicId, Pageable pageable);
}
