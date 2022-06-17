package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.ForumFileDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumFileRepository extends JpaRepository<ForumFileDAO,Long> {
}
