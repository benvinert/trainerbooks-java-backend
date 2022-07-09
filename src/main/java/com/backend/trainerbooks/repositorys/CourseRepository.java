package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.CourseDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseDAO,Long> {
}
