package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.UserDAO;
import com.backend.trainerbooks.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDAO> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserDAO> findById(Long id) {
        return userRepository.findById(id);
    }

    public UserDAO save(UserDAO userEntity) {
        return userRepository.save(userEntity);
    }

    public Optional<UserDAO> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void activateUserByUserId(Long userId) {
        userRepository.activateUserByUserId(userId);
    }



}
