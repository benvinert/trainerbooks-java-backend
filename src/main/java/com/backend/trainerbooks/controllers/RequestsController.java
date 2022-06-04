package com.backend.trainerbooks.controllers;

import com.backend.trainerbooks.DTOS.TrainRequestDTO;
import com.backend.trainerbooks.annotations.SecuredEndPoint;
import com.backend.trainerbooks.entitys.TrainRequestDAO;
import com.backend.trainerbooks.entitys.TraineeDAO;
import com.backend.trainerbooks.entitys.TrainerDAO;
import com.backend.trainerbooks.enums.StatusTrainRequest;
import com.backend.trainerbooks.exceptions.NotFoundEntityException;
import com.backend.trainerbooks.jwt.JWTUtils;
import com.backend.trainerbooks.mappers.IMapDAOToDTOTrainRequest;
import com.backend.trainerbooks.mappers.IMapDTOToDAOTrainRequest;
import com.backend.trainerbooks.services.TrainRequestService;
import com.backend.trainerbooks.services.TraineeAccountService;
import com.backend.trainerbooks.services.TrainerAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.backend.trainerbooks.enums.JWTEnum.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
public class RequestsController {

    private final IMapDAOToDTOTrainRequest mapDAOToDTOTrainRequest;
    private final IMapDTOToDAOTrainRequest mapDTOToDAOTrainRequest;
    private final TrainRequestService trainRequestService;
    private final TrainerAccountService trainerAccountService;
    private final TraineeAccountService traineeAccountService;
    private final JWTUtils jwtUtils;


    @SecuredEndPoint
    @PostMapping("/send-train-request")
    public TrainRequestDTO sendTrainRequest(HttpServletRequest request, @RequestBody TrainRequestDTO trainRequestDTO) throws NotFoundEntityException {
        TrainRequestDAO trainRequestDAO = mapDTOToDAOTrainRequest.map(trainRequestDTO);
        TrainerDAO trainerDAO = trainerAccountService.findTrainerAccountByTrainerId(trainRequestDTO.getTrainerAccount().getId());
        Optional<TraineeDAO> traineeDAO = traineeAccountService.findByTraineeId(trainRequestDTO.getTraineeAccount().getId());
        if (traineeDAO.isPresent()) {
            trainRequestDAO.setTraineeAccount(traineeDAO.get());
            trainRequestDAO.setStatus(StatusTrainRequest.PENDING);
            trainRequestDAO.setTrainerUserId(trainerDAO.getAccountDAO().getUserDAO().getId());
            trainRequestDAO = trainRequestService.save(trainRequestDAO);
        } else {
            throw new NotFoundEntityException(String.format("Trainee Account not found with Id : %s", trainRequestDTO.getTraineeAccount().getId()));
        }
        return mapDAOToDTOTrainRequest.map(trainRequestDAO);
    }

    @SecuredEndPoint
    @GetMapping("/get-all-train-requests-by-uid")
    public List<TrainRequestDTO> getAllTrainRequestsByUid(HttpServletRequest request) {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        List<TrainRequestDAO> trainRequestDAOS = trainRequestService.getAllPendingTrainRequestsByUid(userId);
        return mapDAOToDTOTrainRequest.map(trainRequestDAOS);
    }

    @SecuredEndPoint
    @PutMapping("/update-train-request")
    public TrainRequestDTO updateTrainRequest(HttpServletRequest request, @RequestBody TrainRequestDTO trainRequestDTO) throws NotFoundEntityException {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        StatusTrainRequest trainerChoosedStatus = trainRequestDTO.getStatus();
        if (Objects.equals(userId, trainRequestDTO.getTrainerUserId())) {
            TrainerDAO trainerDAO = trainerAccountService.findTrainerAccountByTrainerId(trainRequestDTO.getTrainerAccount().getId());
            Optional<TraineeDAO> traineeDAO = traineeAccountService.findByTraineeId(trainRequestDTO.getTraineeAccount().getId());
            Optional<TrainRequestDAO> trainRequestDAO = trainRequestService.findById(trainRequestDTO.getId());

            if(traineeDAO.isPresent() && trainerDAO != null && trainRequestDAO.isPresent()) {
                trainerDAO.getTraineeDAO().add(traineeDAO.get());
                traineeDAO.get().setTrainerDAO(trainerDAO);
                trainRequestDAO.get().setStatus(trainerChoosedStatus);

                traineeAccountService.save(traineeDAO.get());
                trainerAccountService.save(trainerDAO);
                trainRequestService.save(trainRequestDAO.get());
            } else {
                throw new NotFoundEntityException(String.format("Not found TraineeDAO with Id : %s, OR TrainerDAO with Id : %s",
                        trainRequestDTO.getTraineeAccount().getId(),trainRequestDTO.getTrainerAccount().getId()));
            }
        }
        return trainRequestDTO;
    }
}
