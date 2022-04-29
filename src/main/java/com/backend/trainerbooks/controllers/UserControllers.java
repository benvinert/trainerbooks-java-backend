package com.backend.trainerbooks.controllers;

import com.backend.trainerbooks.DTOS.UserDTO;
import com.backend.trainerbooks.DTOS.UserOAuthDTO;
import com.backend.trainerbooks.jwt.JWTUtils;
import com.backend.trainerbooks.jwt.JwtResponse;
import com.backend.trainerbooks.entitys.UserDAO;
import com.backend.trainerbooks.mappers.IMapDTOToDAOUser;
import com.backend.trainerbooks.services.GroupUserDetails;
import com.backend.trainerbooks.services.GroupUserDetailsService;
import com.backend.trainerbooks.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


import static com.backend.trainerbooks.enums.JWTEnum.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")
public class UserControllers {

    private final UserService userService;
    private final IMapDTOToDAOUser mapDTOToDAOUser;
    private final AuthenticationManager authenticationManager;
    private final GroupUserDetailsService groupUserDetailsService;
    private final JWTUtils jwtUtils;


    @PostMapping("/saveUser")
    public String saveUser(@Valid @RequestBody UserDTO userDTO) {
        UserDAO userDAO = mapDTOToDAOUser.map(userDTO);
        userService.save(userDAO);
        return "User saved successfully";
    }

    @PostMapping("/oauth/authenticate")
    public Map<String, String> oAuthAuthenticate(@Valid @RequestBody UserOAuthDTO userOAuthDTO) {
        //Secure this endpoint only for nodejs.
        Optional<UserDAO> userDAO = userService.findByEmail(userOAuthDTO.getEmail());
        Map<String, String> responseMap = new HashMap<>();

        userDAO.ifPresentOrElse((user) -> responseMap.put("token", jwtUtils.generateToken(user)), () -> {
                    userOAuthDTO.setPassword(userOAuthDTO.getEmail());//Should find alternative
                    UserDAO userdao = mapDTOToDAOUser.map(userOAuthDTO);
                    userService.save(userdao);
                    responseMap.put("token", jwtUtils.generateToken(userdao));
                }
        );
        return responseMap;
    }

    @GetMapping("/secure/getAllUsers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Collection<UserDAO> getUsers(HttpServletRequest request, HttpServletResponse response) {
        String username = jwtUtils.getUsernameFromToken(request.getHeader(AUTHORIZATION.getValue()));
        return userService.findAllUsers();
    }


    //       "username" : "benoo",
//       "password" : "123"
    @PostMapping("/login")
    //@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public JwtResponse login(@RequestBody UserDAO userEntity) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userEntity.getUsername(),
                    userEntity.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials");
        }
        final GroupUserDetails userDetails = groupUserDetailsService.loadUserByUsername(userEntity.getUsername());
        final String token = jwtUtils.generateToken(userDetails);
        return new JwtResponse(token);
    }
}
