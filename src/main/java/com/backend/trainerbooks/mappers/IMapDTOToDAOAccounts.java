package com.backend.trainerbooks.mappers;

import com.backend.trainerbooks.DTOS.ContactsDTO;
import com.backend.trainerbooks.DTOS.TraineeDTO;
import com.backend.trainerbooks.DTOS.TrainerDTO;
import com.backend.trainerbooks.entitys.AccountDAO;
import com.backend.trainerbooks.entitys.ContactsDAO;
import com.backend.trainerbooks.entitys.TraineeDAO;
import com.backend.trainerbooks.entitys.TrainerDAO;
import com.backend.trainerbooks.enums.SecurityRolesEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring",imports = {ZonedDateTime.class})
public interface IMapDTOToDAOAccounts {

    TrainerDAO map (TrainerDTO trainerDTO);
    TraineeDAO map(TraineeDTO traineeDTO);

    @Mapping(target = "createdDate", expression = "java(ZonedDateTime.now())")
    AccountDAO trainerDTOToAccountDAO (TrainerDTO trainerDTO);
    @Mapping(target = "createdDate", expression = "java(ZonedDateTime.now())")
    AccountDAO traineeDTOToAccountDAO (TraineeDTO traineeDTO);
    ContactsDAO map(ContactsDTO contactsDTO);


}
