package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.ForumTopicDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumTopicRepository extends JpaRepository<ForumTopicDAO,Long> {
}
