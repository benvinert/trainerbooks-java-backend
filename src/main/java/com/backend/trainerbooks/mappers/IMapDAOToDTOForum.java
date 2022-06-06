package com.backend.trainerbooks.mappers;

import com.backend.trainerbooks.DTOS.ForumCategoryDTO;
import com.backend.trainerbooks.entitys.ForumCategoryDAO;
import org.mapstruct.Mapper;

import java.time.ZonedDateTime;
import java.util.List;

@Mapper(componentModel = "spring",imports = {ZonedDateTime.class})
public interface IMapDAOToDTOForum {
    ForumCategoryDTO map(ForumCategoryDAO forumCategoryDAO);
    List<ForumCategoryDTO> map(List<ForumCategoryDAO> forumCategoryDAO);


}
