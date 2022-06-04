package com.backend.trainerbooks.mappers;

import com.backend.trainerbooks.DTOS.TrainRequestDTO;
import com.backend.trainerbooks.entitys.TrainRequestDAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring",imports = {ZonedDateTime.class})
public interface IMapDTOToDAOTrainRequest {

    @Mapping(target = "createdDate", expression = "java(ZonedDateTime.now())")
    TrainRequestDAO map(TrainRequestDTO trainRequestDTO);
}
