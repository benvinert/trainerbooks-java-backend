package com.backend.trainerbooks.mappers.DAOToDTO;

import com.backend.trainerbooks.DTOS.ForumCategoryDTO;
import com.backend.trainerbooks.DTOS.ForumTopicDTO;
import com.backend.trainerbooks.entitys.ForumCategoryDAO;
import com.backend.trainerbooks.entitys.ForumTopicDAO;
import org.mapstruct.Mapper;

import java.time.ZonedDateTime;
import java.util.List;

@Mapper(componentModel = "spring",imports = {ZonedDateTime.class})
public interface IMapDAOToDTOForum {
    ForumCategoryDTO map(ForumCategoryDAO forumCategoryDAO);
    List<ForumCategoryDTO> map(List<ForumCategoryDAO> forumCategoryDAO);

    ForumTopicDTO map(ForumTopicDAO forumTopicDAO);
    List<ForumTopicDTO> mapDAOToDTOTopic(List<ForumTopicDAO> forumTopicDAO);



}
