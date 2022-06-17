package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.ForumTopicDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ForumTopicRepository extends JpaRepository<ForumTopicDAO,Long> {
    List<ForumTopicDAO> findAllByCategoryIdOrderByLastPostCreatedDateDesc(Long categoryId);

    @Modifying
    @Transactional
    @Query(value = "SELECT t from forum_topics t where t.categoryId = :categoryId AND t.tags LIKE :tag")
    List<ForumTopicDAO> findAllTopicsByCategoryIdAndTagOrderByLastPostCreatedDateDesc(Long categoryId,String tag);

}
