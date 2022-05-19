package com.backend.trainerbooks.controllers;

import com.backend.trainerbooks.DTOS.UserDTO;
import com.backend.trainerbooks.DTOS.UserOAuthDTO;
import com.backend.trainerbooks.Responses.LoginResponse;
import com.backend.trainerbooks.entitys.UserDAO;
import com.backend.trainerbooks.jwt.JWTUtils;
import com.backend.trainerbooks.jwt.JwtRequest;
import com.backend.trainerbooks.mappers.IMapDAOToDAOUser;
import com.backend.trainerbooks.mappers.IMapDTOToDAOUser;
import com.backend.trainerbooks.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserControllers {
    Logger logger = LoggerFactory.getLogger(UserControllers.class);
    private final UserService userService;
    private final IMapDTOToDAOUser mapDTOToDAOUser;
    private final AuthenticationManager authenticationManager;
    private final IMapDAOToDAOUser mapDAOToDAOUser;
    private final JWTUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder;


    @PostMapping("/saveUser")
    public Map<String, String> saveUser(@Valid @RequestBody UserDTO userDTO) {
        UserDAO userDAO = mapDTOToDAOUser.map(userDTO);
        Map<String, String> responseMap = new HashMap<>();
        try {
            userService.save(userDAO);
            responseMap.put("hashLink", Base64.getEncoder().encodeToString(userDAO.getId().toString().getBytes()));
        } catch (Exception e) {
            logger.error(String.format("User email with : %s  , ALREADY EXISTS", userDTO.getEmail()), e);
            responseMap.put("emailAlreadyExists", "User Email already exists");
        }
        return responseMap;
    }

    @GetMapping("/activate/user/{useridBase64}")
    public Map<String, Boolean> activateUser(@PathVariable String useridBase64) {
        Long userid = Long.valueOf(new String(Base64.getDecoder().decode(useridBase64)));
        userService.activateUserByUserId(userid);
        return Map.of("Success", true);
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

    @PostMapping("/signin")
    public LoginResponse login(@RequestBody UserDTO userDTO) {
        Optional<UserDAO> userDAO = null;
        String errorMessage = "";
        String jwtToken = null;
        boolean error = false;
        try {
            userDAO = userService.findByEmail(userDTO.getEmail());

            if (userDAO.isPresent()) {
                if (!passwordEncoder.matches(userDTO.getPassword(), userDAO.get().getPassword())) {
                    throw new Exception("Password incorrect");
                }
                jwtToken = jwtUtils.generateToken(userDAO.get());
            } else {
                throw new Exception("Email incorrect");
            }
        } catch (Exception e) {
            logger.warn(String.format("Invalid Credentials , Userid :  %s , email : %s", userDTO.getId(), userDTO.getEmail()));
            errorMessage = e.getMessage();
            error = true;
        }
        return new LoginResponse(error, jwtToken, errorMessage);
    }

    @PostMapping("/get-user-by-token")
    public UserDTO getUserByToken(HttpServletResponse response, @RequestBody JwtRequest jwtToken) {
        UserDTO userDTO = new UserDTO();
        if (jwtToken.getJwtToken() != null && Boolean.FALSE.equals(jwtUtils.isTokenExpired(jwtToken.getJwtToken()))) {
            Long userId = jwtUtils.getIdFromToken(jwtToken.getJwtToken());
            Optional<UserDAO> userDAO = userService.findById(userId);
            if (userDAO.isPresent()) {
                userDTO = mapDAOToDAOUser.map(userDAO.get());
                userDTO.setLoggedIn(true);
            }
        } else {
            userDTO.setLoggedIn(false);
        }
        return userDTO;
    }
}
