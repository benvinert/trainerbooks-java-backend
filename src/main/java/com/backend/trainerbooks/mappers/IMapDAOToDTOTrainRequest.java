package com.backend.trainerbooks.mappers;

import com.backend.trainerbooks.DTOS.TrainRequestDTO;
import com.backend.trainerbooks.entitys.TrainRequestDAO;
import com.backend.trainerbooks.entitys.TraineeDAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZonedDateTime;
import java.util.List;

@Mapper(componentModel = "spring",imports = {ZonedDateTime.class})
public interface IMapDAOToDTOTrainRequest {

    @Mapping(target = "createdDate", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "traineeAccount.fullName",expression = "java(getFullName(traineeDAO))")
    TrainRequestDTO map(TrainRequestDAO trainRequestDAO);
    List<TrainRequestDTO> map(List<TrainRequestDAO> trainRequestDAOList);

    default String getFullName(TraineeDAO traineeDAO) {
        return traineeDAO.getAccountDAO().getUserDAO().getFirstName() + " " + traineeDAO.getAccountDAO().getUserDAO().getLastName();

    }
}
