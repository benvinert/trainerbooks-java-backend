package com.backend.trainerbooks.controllers.secure;

import com.backend.trainerbooks.DTOS.AccountDTO;
import com.backend.trainerbooks.DTOS.TraineeDTO;
import com.backend.trainerbooks.DTOS.TrainerDTO;
import com.backend.trainerbooks.entitys.AccountDAO;
import com.backend.trainerbooks.entitys.TraineeDAO;
import com.backend.trainerbooks.entitys.TrainerDAO;
import com.backend.trainerbooks.entitys.UserDAO;
import com.backend.trainerbooks.jwt.JWTUtils;
import com.backend.trainerbooks.mappers.IMapDTOToDAOAccounts;
import com.backend.trainerbooks.services.AccountService;
import com.backend.trainerbooks.services.TraineeAccountService;
import com.backend.trainerbooks.services.TrainerAccountService;
import com.backend.trainerbooks.services.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.ZonedDateTimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.time.ZonedDateTime;
import java.util.Optional;

import static com.backend.trainerbooks.enums.JWTEnum.AUTHORIZATION;

@RestController
@RequestMapping("/secure")
@RequiredArgsConstructor
public class AccountControllers {

    private final JWTUtils jwtUtils;
    private final TrainerAccountService trainerAccountService;
    private final TraineeAccountService traineeAccountService;
    private final UserService userService;
    private final IMapDTOToDAOAccounts mapDTOToDAOAccounts;
    private final AccountService accountService;

    @PostMapping("/create-trainer-account")
    public TrainerDAO createAccountForTrainer(HttpServletRequest request,@RequestBody TrainerDTO trainerDTO) {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        TrainerDAO trainerDAO = null;
        if (userId != null) {
            AccountDAO accountDAO = mapDTOToDAOAccounts.trainerDTOToAccountDAO(trainerDTO);
            Optional<UserDAO> userDAO = userService.findById(userId);
            accountDAO.setUserDAO(userDAO.orElseThrow());
            accountService.save(accountDAO);

            trainerDAO = mapDTOToDAOAccounts.map(trainerDTO);
            trainerDAO.setAccountDAO(accountDAO);
            trainerAccountService.save(trainerDAO);
        }
        return trainerDAO;
    }

    @PostMapping("/create-trainee-account")
    public TraineeDAO createAccountForTrainee(HttpServletRequest request,@RequestBody TraineeDTO traineeDTO) {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        TraineeDAO traineeDAO = null;
        if(userId != null) {
            AccountDAO accountDAO = mapDTOToDAOAccounts.traineeDTOToAccountDAO(traineeDTO);
            Optional<UserDAO> userDAO = userService.findById(userId);
            accountDAO.setUserDAO(userDAO.orElseThrow());
            accountService.save(accountDAO);
            
            traineeDAO = mapDTOToDAOAccounts.map(traineeDTO);
            traineeDAO.setAccountDAO(accountDAO);
            traineeAccountService.save(traineeDAO);
        }

        return traineeDAO;
    }
}
