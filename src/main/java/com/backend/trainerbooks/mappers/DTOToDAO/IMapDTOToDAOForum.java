package com.backend.trainerbooks.mappers.DTOToDAO;

import com.backend.trainerbooks.DTOS.ForumPostDTO;
import com.backend.trainerbooks.DTOS.ForumTopicDTO;
import com.backend.trainerbooks.DTOS.UserDTO;
import com.backend.trainerbooks.entitys.ForumPostDAO;
import com.backend.trainerbooks.entitys.ForumTopicDAO;
import com.backend.trainerbooks.entitys.UserDAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface IMapDTOToDAOForum {
    UserDAO map(UserDTO userDTO);

    @Mapping(target = "tags", expression = "java(getTagsAsList(forumTopicDTO))")
    ForumTopicDAO map(ForumTopicDTO forumTopicDTO);
    List<ForumTopicDAO> map(List<ForumTopicDTO> forumTopicDTO);

    ForumPostDAO map(ForumPostDTO forumPostDTO);
    List<ForumPostDAO> mapForumPost(List<ForumPostDTO> forumPostDTOS);

    default String getTagsAsList(ForumTopicDTO forumTopicDTO) {
        return String.join(",",forumTopicDTO.getTags());
    }
}
