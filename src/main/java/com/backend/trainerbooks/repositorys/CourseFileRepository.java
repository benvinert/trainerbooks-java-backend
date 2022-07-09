package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.CourseFileDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseFileRepository extends JpaRepository<CourseFileDAO,Long> {
}
