package com.backend.trainerbooks.mappers;

import com.backend.trainerbooks.DTOS.ContactsDTO;
import com.backend.trainerbooks.DTOS.TraineeDTO;
import com.backend.trainerbooks.DTOS.TrainerDTO;
import com.backend.trainerbooks.entitys.AccountDAO;
import com.backend.trainerbooks.entitys.ContactsDAO;
import com.backend.trainerbooks.entitys.TraineeDAO;
import com.backend.trainerbooks.entitys.TrainerDAO;
import com.backend.trainerbooks.entitys.UserDAO;
import com.backend.trainerbooks.enums.SecurityRolesEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface IMapDAOToDTOAccounts {
    @Mapping(target = "createdDate", source = "traineeDAO.accountDAO.createdDate")
    @Mapping(target = "category",source = "traineeDAO.accountDAO.category")
    @Mapping(target = "fullName",expression = "java(getFullName(traineeDAO.getAccountDAO().getUserDAO()))")
    TraineeDTO map (TraineeDAO traineeDAO);
    @Mapping(target = "createdDate", source = "trainerDAO.accountDAO.createdDate")
    @Mapping(target = "category",source = "trainerDAO.accountDAO.category")
    @Mapping(target = "fullName",expression = "java(getFullName(trainerDAO.getAccountDAO().getUserDAO()))")
    @Mapping(target = "userId",source = "trainerDAO.accountDAO.userDAO.id")
    TrainerDTO map (TrainerDAO trainerDAO);
    List<TraineeDTO> traineeDTOtoDAO (List<TraineeDAO> traineeDAOS);
    List<TrainerDTO> map(List<TrainerDAO> trainerDAOS);

    default String getFullName(UserDAO userDAO) {
        return userDAO.getFirstName() + " " + userDAO.getLastName();
    }

    ContactsDTO map(ContactsDAO contactsDAO);
    List<ContactsDTO> mapDAOToDTOContacts(List<ContactsDAO> contactsDAO);


}
