package com.backend.trainerbooks.mappers;

import com.backend.trainerbooks.DTOS.TraineeDTO;
import com.backend.trainerbooks.DTOS.TrainerDTO;
import com.backend.trainerbooks.entitys.TraineeDAO;
import com.backend.trainerbooks.entitys.TrainerDAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface IMapDAOToDTOAccounts {
    @Mapping(target = "createdDate", source = "traineeDAO.accountDAO.createdDate")
    @Mapping(target = "category",source = "traineeDAO.accountDAO.category")
    TraineeDTO map (TraineeDAO traineeDAO);
    TrainerDTO map (TrainerDAO trainerDAO);

    List<TraineeDTO> traineeDTOtoDAO (List<TraineeDAO> traineeDAOS);

    List<TrainerDTO> map(List<TrainerDAO> trainerDAOS);
}
