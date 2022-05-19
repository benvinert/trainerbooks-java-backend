package com.backend.trainerbooks.controllers;

import com.backend.trainerbooks.DTOS.TraineeDTO;
import com.backend.trainerbooks.DTOS.TrainerDTO;
import com.backend.trainerbooks.annotations.SecuredEndPoint;
import com.backend.trainerbooks.entitys.AccountDAO;
import com.backend.trainerbooks.entitys.TraineeDAO;
import com.backend.trainerbooks.entitys.TrainerDAO;
import com.backend.trainerbooks.entitys.UserDAO;
import com.backend.trainerbooks.jwt.JWTUtils;
import com.backend.trainerbooks.mappers.IMapDAOToDTOAccounts;
import com.backend.trainerbooks.mappers.IMapDTOToDAOAccounts;
import com.backend.trainerbooks.services.AccountService;
import com.backend.trainerbooks.services.NativeQueryService;
import com.backend.trainerbooks.services.TraineeAccountService;
import com.backend.trainerbooks.services.TrainerAccountService;
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
import java.util.List;
import java.util.Optional;

import static com.backend.trainerbooks.enums.JWTEnum.AUTHORIZATION;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")
public class AccountControllers {

    private final JWTUtils jwtUtils;
    private final TrainerAccountService trainerAccountService;
    private final TraineeAccountService traineeAccountService;
    private final UserService userService;
    private final IMapDTOToDAOAccounts mapDTOToDAOAccounts;
    private final AccountService accountService;
    private final IMapDAOToDTOAccounts mapDAOToDTOAccounts;

    @SecuredEndPoint
    @PostMapping("/create-trainer-account")
    public TrainerDTO createAccountForTrainer(HttpServletRequest request, HttpServletResponse response, @RequestBody TrainerDTO trainerDTO) {
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
            mapDAOToDTOAccounts.map(trainerDAO);
            response.setStatus(HttpStatus.OK.value());
            trainerDTO.setId(trainerDAO.getId());
        }
        return trainerDTO;
    }

    @SecuredEndPoint
    @PostMapping("/create-trainee-account")
    public TraineeDAO secureCreateAccountForTrainee(HttpServletRequest request,@Valid @RequestBody TraineeDTO traineeDTO) {
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


    @SecuredEndPoint
    @GetMapping("/get-trainee-accounts")
    public List<TraineeDTO> getTraineeAccounts(HttpServletRequest request){
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        List<TraineeDAO> traineeDAOS = traineeAccountService.findAllTraineeAccountsByUserId(userId);
        return mapDAOToDTOAccounts.traineeDTOtoDAO(traineeDAOS);
    }

    @SecuredEndPoint
    @GetMapping("/get-trainer-accounts")
    public List<TrainerDTO> getTrainerAccounts(HttpServletRequest request){
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        List<TrainerDAO> trainerDAOS =  trainerAccountService.findAllTrainerAccountsByUserId(userId);
        return mapDAOToDTOAccounts.map(trainerDAOS);
    }


    @GetMapping("/get-trainer-account/{accountId}")
    public TrainerDTO getTrainerAccountById(HttpServletRequest request, @PathVariable String accountId) {
        TrainerDAO trainerDAO = trainerAccountService.findTrainerAccountByTrainerId(Long.parseLong(accountId));
        TrainerDTO trainerDTO = mapDAOToDTOAccounts.map(trainerDAO);
        return trainerDTO;
    }

    @GetMapping("/get-trainee-account/{accountId}")
    public TraineeDTO getTraineeAccountById(HttpServletResponse response, @PathVariable String accountId) {
        Optional<TraineeDAO> traineeDAO = traineeAccountService.findByTraineeId(Long.parseLong(accountId));
        TraineeDTO traineeDTO = null;
        if(traineeDAO.isPresent()) {
            traineeDTO = mapDAOToDTOAccounts.map(traineeDAO.get());
        } else {
            response.setStatus(HttpStatus.NO_CONTENT.value());
        }
        return traineeDTO;
    }

    @PostMapping("/add-trainee-to-trainer")
    public TraineeDTO addTraineeToTrainer(@RequestBody TraineeDTO traineeDTO) {
        Optional<TraineeDAO> traineeDAO = traineeAccountService.findByTraineeId(traineeDTO.getId());
        TrainerDAO trainerDAO = trainerAccountService.findTrainerAccountByTrainerId(traineeDTO.getTrainerDTO().getId());
        if(traineeDAO.isPresent()) {
            trainerDAO.getTraineeDAO().add(traineeDAO.get());
            traineeDAO.get().setTrainerDAO(trainerDAO);
            traineeAccountService.save(traineeDAO.get());
            trainerAccountService.save(trainerDAO);
        }
        return traineeDTO;
    }

    @GetMapping("/get-all-trainer-accounts")
    public List<TrainerDTO> getAllTrainerAccounts() {
        List<TrainerDAO> trainerDAOS = trainerAccountService.findAllTrainerAccountsByRank();
        List<TrainerDTO> trainerDTOS = mapDAOToDTOAccounts.map(trainerDAOS);
        return trainerDTOS;
    }
}
