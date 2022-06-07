package com.backend.trainerbooks.mappers.DTOToDAO;

import com.backend.trainerbooks.DTOS.ContactsDTO;
import com.backend.trainerbooks.DTOS.TraineeDTO;
import com.backend.trainerbooks.DTOS.TrainerDTO;
import com.backend.trainerbooks.entitys.AccountDAO;
import com.backend.trainerbooks.entitys.ContactsDAO;
import com.backend.trainerbooks.entitys.TraineeDAO;
import com.backend.trainerbooks.entitys.TrainerDAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
public interface IMapDTOToDAOAccounts {

    TrainerDAO map (TrainerDTO trainerDTO);
    TraineeDAO map(TraineeDTO traineeDTO);

    AccountDAO trainerDTOToAccountDAO (TrainerDTO trainerDTO);
    AccountDAO traineeDTOToAccountDAO (TraineeDTO traineeDTO);

    ContactsDAO map(ContactsDTO contactsDTO);


}
