package com.backend.trainerbooks.mappers;

import com.backend.trainerbooks.DTOS.UserDTO;
import com.backend.trainerbooks.DTOS.UserOAuthDTO;
import com.backend.trainerbooks.entitys.UserDAO;
import com.backend.trainerbooks.enums.SecurityRolesEnum;
import com.backend.trainerbooks.utils.SecurityUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.util.StringUtils;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring",imports = {ZonedDateTime.class, SecurityRolesEnum.class},uses = {SecurityUtils.class})
public interface IMapDTOToDAOUser {
    @Mapping(target = "createdDate", expression = "java(ZonedDateTime.now())")
    @Mapping(source = "password",target = "password", qualifiedByName = "passwordEncoder")
    @Mapping(target = "isActive" , defaultValue = "false")
    @Mapping(target = "roles" , expression = "java(getUserRole())")
    UserDAO map(UserDTO userDTO);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createdDate", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "isActive" , defaultValue = "true")
    @Mapping(source = "password",target = "password", qualifiedByName = "passwordEncoder")
    @Mapping(target = "roles" , expression = "java(getUserRole())")
    UserDAO map(UserOAuthDTO userOAuthDTO);


    default String getUserRole() {
        return SecurityRolesEnum.ROLE_USER.getRole();
    }

    default String trimSpaces(String username) {
        return !StringUtils.hasLength(username) ?  username : username.replaceAll("\\s+","");
    }

}
