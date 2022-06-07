package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.ForumCategoryDAO;
import com.backend.trainerbooks.entitys.ForumTopicDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumCategoryRepository extends JpaRepository<ForumCategoryDAO,Long> {
}
