package com.backend.trainerbooks.controllers;

import com.backend.trainerbooks.DTOS.ForumCategoryDTO;
import com.backend.trainerbooks.DTOS.ForumTopicDTO;
import com.backend.trainerbooks.annotations.SecuredEndPoint;
import com.backend.trainerbooks.entitys.ForumCategoryDAO;
import com.backend.trainerbooks.entitys.ForumPostDAO;
import com.backend.trainerbooks.entitys.ForumTopicDAO;
import com.backend.trainerbooks.entitys.UserDAO;
import com.backend.trainerbooks.enums.ForumCategoryEnum;
import com.backend.trainerbooks.jwt.JWTUtils;
import com.backend.trainerbooks.mappers.DAOToDTO.IMapDAOToDTOForum;
import com.backend.trainerbooks.mappers.DTOToDAO.IMapDTOToDAOForum;
import com.backend.trainerbooks.services.ForumCategoryService;
import com.backend.trainerbooks.services.ForumPostService;
import com.backend.trainerbooks.services.ForumTopicService;
import com.backend.trainerbooks.services.UserService;
import com.backend.trainerbooks.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.backend.trainerbooks.enums.JWTEnum.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class ForumController {

    private final IMapDTOToDAOForum mapDTOToDAOForum;
    private final IMapDAOToDTOForum mapDAOToDTOForum;
    private final ForumCategoryService forumCategoryService;
    private final ForumTopicService forumTopicService;
    private final ForumPostService forumPostService;
    private final JWTUtils jwtUtils;
    private final ValidationUtils validationUtils;
    private final UserService userService;

    @GetMapping("/get-all-categories")
    public List<ForumCategoryDTO> findAllCategories() {
        List<ForumCategoryDAO> forumCategoryDAOS = forumCategoryService.findAllCategories();
        return mapDAOToDTOForum.map(forumCategoryDAOS);
    }

    @GetMapping("/get-all-topics-by-url/{urlCategory}")
    public List<ForumTopicDTO> getAllTopicsByUrl(@PathVariable String urlCategory) {
        List<ForumTopicDAO> forumTopicDAOS = forumTopicService.findAllForumTopicsByUrl(urlCategory);
        return mapDAOToDTOForum.mapDAOToDTOTopic(forumTopicDAOS);
    }

    @SecuredEndPoint
    @PostMapping("/add-topic-by-category")
    public ForumTopicDTO addTopicByCategory(HttpServletRequest request, @RequestBody @Valid ForumTopicDTO forumTopicDTO) {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        boolean isValidCategory = Arrays.stream(ForumCategoryEnum.values())
                .anyMatch(eachEnum -> eachEnum.getValue() == forumTopicDTO.getForumCategory().getId());

        if (!validationUtils.isContainsURL(forumTopicDTO.getTitle()) &&
                !validationUtils.isContainsURL(forumTopicDTO.getPosts().get(0).getPostText()) && isValidCategory) {
            Optional<UserDAO> userDAO = userService.findById(userId);
            if (userDAO.isPresent()) {
                forumTopicDTO.setCreatedDate(ZonedDateTime.now());
                ForumTopicDAO forumTopicDAO = mapDTOToDAOForum.map(forumTopicDTO);
                forumTopicDAO.setByUser(userDAO.get());

                List<ForumPostDAO> postDAOS = forumTopicDAO.getPosts();
                if(!postDAOS.isEmpty() && postDAOS.get(0) != null) {
                    ForumPostDAO forumPostDAO = postDAOS.get(0);
                    forumPostDAO.setLikes(0L);
                    forumPostDAO.setForumTopic(forumTopicDAO);
                    forumPostDAO.setByUser(userDAO.get());
                    forumTopicDAO.setNumOfPosts( (long) (forumTopicDAO.getPosts().size()) );
                    forumTopicService.save(forumTopicDAO);
                }
            }
        }
        return forumTopicDTO;
    }
}
