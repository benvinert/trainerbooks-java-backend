package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.ForumCategoryDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumCategoryRepository extends JpaRepository<ForumCategoryDAO,Long> {
}
