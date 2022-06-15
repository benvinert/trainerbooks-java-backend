package com.backend.trainerbooks.mappers.DAOToDTO;

import com.backend.trainerbooks.DTOS.ForumCategoryDTO;
import com.backend.trainerbooks.DTOS.ForumPostDTO;
import com.backend.trainerbooks.DTOS.ForumTopicDTO;
import com.backend.trainerbooks.entitys.ForumCategoryDAO;
import com.backend.trainerbooks.entitys.ForumPostDAO;
import com.backend.trainerbooks.entitys.ForumTopicDAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZonedDateTime;
import java.util.List;

@Mapper(componentModel = "spring",imports = {ZonedDateTime.class})
public interface IMapDAOToDTOForum {
    ForumCategoryDTO map(ForumCategoryDAO forumCategoryDAO);
    List<ForumCategoryDTO> map(List<ForumCategoryDAO> forumCategoryDAO);

    ForumPostDTO map(ForumPostDAO forumPostDAO);
    @Mapping(target = "tags", expression = "java(getTagsAsList(forumTopicDAO))")
    ForumTopicDTO map(ForumTopicDAO forumTopicDAO);
    List<ForumTopicDTO> mapDAOToDTOTopic(List<ForumTopicDAO> forumTopicDAO);

    default List<String> getTagsAsList(ForumTopicDAO forumTopicDAO) {
        return List.of(forumTopicDAO.getTags().split(","));
    }

}
