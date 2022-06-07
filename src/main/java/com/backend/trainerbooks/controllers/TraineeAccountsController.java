package com.backend.trainerbooks.controllers;

import com.backend.trainerbooks.DTOS.TraineeDTO;
import com.backend.trainerbooks.annotations.SecuredEndPoint;
import com.backend.trainerbooks.entitys.AccountDAO;
import com.backend.trainerbooks.entitys.TraineeDAO;
import com.backend.trainerbooks.entitys.UserDAO;
import com.backend.trainerbooks.exceptions.NotFoundEntityException;
import com.backend.trainerbooks.jwt.JWTUtils;
import com.backend.trainerbooks.mappers.DAOToDTO.IMapDAOToDTOAccounts;
import com.backend.trainerbooks.mappers.DTOToDAO.IMapDTOToDAOAccounts;
import com.backend.trainerbooks.services.AccountService;
import com.backend.trainerbooks.services.TraineeAccountService;
import com.backend.trainerbooks.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static com.backend.trainerbooks.constants.AccountConstants.DEFAULT_IMAGE_NAME;
import static com.backend.trainerbooks.enums.JWTEnum.AUTHORIZATION;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TraineeAccountsController {

    private final JWTUtils jwtUtils;
    private final TraineeAccountService traineeAccountService;
    private final UserService userService;
    private final IMapDTOToDAOAccounts mapDTOToDAOAccounts;
    private final AccountService accountService;
    private final IMapDAOToDTOAccounts mapDAOToDTOAccounts;

    @SecuredEndPoint
    @PostMapping("/create-trainee-account")
    public TraineeDAO secureCreateAccountForTrainee(HttpServletRequest request, @Valid @RequestBody TraineeDTO traineeDTO) {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        TraineeDAO traineeDAO = null;
        if (userId != null) {
            traineeDTO.setCreatedDate(ZonedDateTime.now());
            AccountDAO accountDAO = mapDTOToDAOAccounts.traineeDTOToAccountDAO(traineeDTO);
            Optional<UserDAO> userDAO = userService.findById(userId);
            accountDAO.setUserDAO(userDAO.orElseThrow());
            accountService.save(accountDAO);

            traineeDAO = mapDTOToDAOAccounts.map(traineeDTO);
            traineeDAO.setAccountDAO(accountDAO);
            traineeDAO.setBeforeImage(DEFAULT_IMAGE_NAME);
            traineeDAO.setAfterImage(DEFAULT_IMAGE_NAME);
            traineeAccountService.save(traineeDAO);
        }

        return traineeDAO;
    }

    @SecuredEndPoint
    @GetMapping("/get-trainee-accounts")
    public List<TraineeDTO> getTraineeAccounts(HttpServletRequest request) {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        List<TraineeDAO> traineeDAOS = traineeAccountService.findAllTraineeAccountsByUserId(userId);
        return mapDAOToDTOAccounts.traineeDTOtoDAO(traineeDAOS);
    }

    @GetMapping("/get-trainee-account/{accountId}")
    public TraineeDTO getTraineeAccountById(HttpServletResponse response, @PathVariable String accountId) {
        Optional<TraineeDAO> traineeDAO = traineeAccountService.findByTraineeId(Long.parseLong(accountId));
        TraineeDTO traineeDTO = null;
        if (traineeDAO.isPresent()) {
            traineeDTO = mapDAOToDTOAccounts.map(traineeDAO.get());
        } else {
            response.setStatus(HttpStatus.NO_CONTENT.value());
        }
        return traineeDTO;
    }

    @SecuredEndPoint
    @GetMapping("/get-trainee-account-by-category/{category}")
    public TraineeDTO getTraineeAccountByUserAndCategory(HttpServletRequest request, @PathVariable String category) throws NotFoundEntityException {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        TraineeDTO traineeDTO;
        Optional<TraineeDAO> traineeDAO = traineeAccountService.findByUserAndCategory(userId,category);
        if(traineeDAO.isPresent()) {
            traineeDTO = mapDAOToDTOAccounts.map(traineeDAO.get());
        } else {
            throw new NotFoundEntityException(String.format("Account not found with UserId : %s , And Category : %s",userId.toString() , category));
        }
        return traineeDTO;
    }
}
