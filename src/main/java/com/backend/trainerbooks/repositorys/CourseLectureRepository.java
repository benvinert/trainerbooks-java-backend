package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.CourseLectureDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseLectureRepository extends JpaRepository<CourseLectureDAO,Long> {

}
