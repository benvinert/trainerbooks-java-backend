package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDAO,Integer> {
    Optional<UserDAO> findByUsername(String username);
    Optional<UserDAO> findById(Long id);
    Optional<UserDAO> findByEmail(String email);


}
