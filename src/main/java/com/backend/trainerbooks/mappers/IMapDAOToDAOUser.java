package com.backend.trainerbooks.mappers;

import com.backend.trainerbooks.DTOS.UserDTO;
import com.backend.trainerbooks.entitys.UserDAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IMapDAOToDAOUser {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "birthdate", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserDTO map(UserDAO userDAO);
}
