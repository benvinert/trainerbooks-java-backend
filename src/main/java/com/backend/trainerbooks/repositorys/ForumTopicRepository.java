package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.ForumTopicDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumTopicRepository extends JpaRepository<ForumTopicDAO,Long> {
    List<ForumTopicDAO> findAllByCategoryIdOrderByCreatedDateDesc(Long categoryId);

}
