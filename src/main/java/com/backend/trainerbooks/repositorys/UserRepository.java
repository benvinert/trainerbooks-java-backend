package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.QueryHint;
import java.util.Optional;

import static org.hibernate.jpa.QueryHints.HINT_CACHEABLE;

public interface UserRepository extends JpaRepository<UserDAO,Integer> {

    Optional<UserDAO> findById(Long id);
    Optional<UserDAO> findByEmail(String email);

    @Modifying
    @Transactional //helps with exception: Executing an update/delete query
    @Query("update users u set u.isActive = true where u.id = :userid")
    void activateUserByUserId(@Param("userid") Long userid);


}
