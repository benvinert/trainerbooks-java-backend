package com.backend.trainerbooks.controllers;

import com.backend.trainerbooks.DTOS.TraineeDTO;
import com.backend.trainerbooks.DTOS.TraineeTransformationDTO;
import com.backend.trainerbooks.DTOS.TrainerDTO;
import com.backend.trainerbooks.annotations.SecuredEndPoint;
import com.backend.trainerbooks.entitys.AccountDAO;
import com.backend.trainerbooks.entitys.TraineeDAO;
import com.backend.trainerbooks.entitys.TrainerDAO;
import com.backend.trainerbooks.entitys.UserDAO;
import com.backend.trainerbooks.exceptions.AuthException;
import com.backend.trainerbooks.jwt.JWTUtils;
import com.backend.trainerbooks.mappers.DAOToDTO.IMapDAOToDTOAccounts;
import com.backend.trainerbooks.mappers.DTOToDAO.IMapDTOToDAOAccounts;
import com.backend.trainerbooks.services.AccountService;
import com.backend.trainerbooks.services.TrainerAccountService;
import com.backend.trainerbooks.services.UserService;
import com.backend.trainerbooks.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.backend.trainerbooks.enums.JWTEnum.AUTHORIZATION;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TrainerAccountsController {

    private final JWTUtils jwtUtils;
    private final TrainerAccountService trainerAccountService;
    private final UserService userService;
    private final IMapDTOToDAOAccounts mapDTOToDAOAccounts;
    private final AccountService accountService;
    private final IMapDAOToDTOAccounts mapDAOToDTOAccounts;
    private final AccountUtils accountUtils;

    @SecuredEndPoint
    @PostMapping("/create-trainer-account")
    public TrainerDTO createAccountForTrainer(HttpServletRequest request, HttpServletResponse response, @RequestBody TrainerDTO trainerDTO) {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        TrainerDAO trainerDAO = null;
        if (userId != null) {
            trainerDTO.setCreatedDate(ZonedDateTime.now());
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
    @GetMapping("/get-trainer-accounts")
    public List<TrainerDTO> getTrainerAccounts(HttpServletRequest request) {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        List<TrainerDAO> trainerDAOS = trainerAccountService.findAllTrainerAccountsByUserId(userId);
        return mapDAOToDTOAccounts.map(trainerDAOS);
    }

    @GetMapping("/get-trainer-account/{accountId}")
    public TrainerDTO getTrainerAccountById(HttpServletRequest request, @PathVariable String accountId) {
        TrainerDAO trainerDAO = trainerAccountService.findTrainerAccountByTrainerId(Long.parseLong(accountId));
        TrainerDTO trainerDTO = mapDAOToDTOAccounts.map(trainerDAO);
        return trainerDTO;
    }

    @GetMapping("/get-all-trainer-accounts")
    public List<TrainerDTO> getAllTrainerAccounts() {
        List<TrainerDAO> trainerDAOS = trainerAccountService.findAllTrainerAccountsByRank();
        List<TrainerDTO> trainerDTOS = mapDAOToDTOAccounts.map(trainerDAOS);
        return trainerDTOS;
    }

    @SecuredEndPoint
    @PutMapping("/edit-trainer-account")
    public TrainerDTO editTrainerAccount(HttpServletRequest request, @RequestBody TrainerDTO trainerDTO) throws AuthException {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        TrainerDAO trainerDAO = trainerAccountService.findTrainerAccountByTrainerId(trainerDTO.getId());
        if (Objects.equals(trainerDAO.getAccountDAO().getUserDAO().getId(), userId)) {
            if (accountUtils.fieldHasLengthMoreThan(trainerDTO.getAboutProcess(), 5)) {
                trainerDAO.setAboutProcess(trainerDTO.getAboutProcess());
            }
            if (accountUtils.fieldHasLengthMoreThan(trainerDTO.getWhoIs(), 5)) {
                trainerDAO.setWhoIs(trainerDTO.getWhoIs());
            }
            trainerAccountService.save(trainerDAO);
        } else {
            throw new AuthException("Permission Denied");
        }

        return mapDAOToDTOAccounts.map(trainerDAO);
    }

    @SecuredEndPoint
    @PutMapping("/edit-trainer-trainees-transformations")
    public List<TraineeDTO> editTrainerTraineesTransformations(HttpServletRequest request,@RequestBody TraineeTransformationDTO traineeTransformationDTO) {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        TrainerDAO trainerDAO = trainerAccountService.findTrainerAccountByTrainerId(traineeTransformationDTO.getTrainerDTO().getId());
        if (Objects.equals(trainerDAO.getAccountDAO().getUserDAO().getId(), userId)) {
            List<TraineeDAO> traineeTransformationsDAOs = new LinkedList<>();
            for(TraineeDTO traineeDTO : traineeTransformationDTO.getTraineeDTOS()) {
                Optional<TraineeDAO> traineeDAO = trainerDAO.getTrainees()
                        .stream()
                        .filter((eachTraineeDAO -> Objects.equals(eachTraineeDAO.getId(), traineeDTO.getId())))
                        .findFirst();
                traineeDAO.ifPresent(traineeTransformationsDAOs::add);
            }
            trainerDAO.setTraineeTransformations(traineeTransformationsDAOs);
            trainerDAO = trainerAccountService.save(trainerDAO);
        }
        return mapDAOToDTOAccounts.map(trainerDAO).getTraineeTransformations();
    }
}
