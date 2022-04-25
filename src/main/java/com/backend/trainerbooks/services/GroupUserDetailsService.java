package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.UserDAO;
import com.backend.trainerbooks.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public GroupUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDAO> userModel =  userRepository.findByUsername(username);

        return userModel.map(GroupUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(username + "NOT EXISTS."));
    }


}
