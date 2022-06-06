package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.ForumPostDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumPostRepository extends JpaRepository<ForumPostDAO,Long> {
}
