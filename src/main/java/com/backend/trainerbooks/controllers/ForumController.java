package com.backend.trainerbooks.controllers;

import com.backend.trainerbooks.DTOS.ForumCategoryDTO;
import com.backend.trainerbooks.DTOS.ForumPostDTO;
import com.backend.trainerbooks.DTOS.ForumTopicDTO;
import com.backend.trainerbooks.annotations.SecuredEndPoint;
import com.backend.trainerbooks.entitys.ForumCategoryDAO;
import com.backend.trainerbooks.entitys.ForumPostDAO;
import com.backend.trainerbooks.entitys.ForumTopicDAO;
import com.backend.trainerbooks.entitys.UserDAO;
import com.backend.trainerbooks.enums.ForumCategoryEnum;
import com.backend.trainerbooks.exceptions.NotFoundEntityException;
import com.backend.trainerbooks.jwt.JWTUtils;
import com.backend.trainerbooks.mappers.DAOToDTO.IMapDAOToDTOForum;
import com.backend.trainerbooks.mappers.DTOToDAO.IMapDTOToDAOForum;
import com.backend.trainerbooks.services.ForumCategoryService;
import com.backend.trainerbooks.services.ForumPostService;
import com.backend.trainerbooks.services.ForumTopicService;
import com.backend.trainerbooks.services.UserService;
import com.backend.trainerbooks.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.backend.trainerbooks.enums.JWTEnum.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class ForumController {
    Logger logger = LoggerFactory.getLogger(ForumController.class);

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

    @GetMapping("/get-all-topics-by-category-url/{categoryUrl}")
    public Map<String, Object> getAllTopicsByUrl(@PathVariable String categoryUrl, String tag) {
        ForumCategoryDAO forumCategoryDAO = forumCategoryService.findByUrl(categoryUrl);
        List<ForumTopicDAO> forumTopicDAOS = new LinkedList<>();
        try {
            if (tag != null) {
                forumTopicDAOS = forumTopicService.findAllForumTopicsByCategoryIdAndTagContainsOrderByDate(forumCategoryDAO.getId(), tag.toLowerCase());
            } else {
                forumTopicDAOS = forumTopicService.findAllForumTopicsByCategoryIdAndOrderByDate(forumCategoryDAO.getId());
            }
        } catch (Exception e) {
            logger.error("Error in findForumTopics ", e);
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("topics", mapDAOToDTOForum.mapDAOToDTOTopic(forumTopicDAOS));
        responseMap.put("category", mapDAOToDTOForum.map(forumCategoryDAO));
        return responseMap;
    }

    @GetMapping("/get-category-by-url/{categoryUrl}")
    public ForumCategoryDTO getCategoryByUrl(@PathVariable String categoryUrl) {
        ForumCategoryDAO forumCategoryDAO = forumCategoryService.findByUrl(categoryUrl);
        return mapDAOToDTOForum.map(forumCategoryDAO);
    }

    @SecuredEndPoint
    @PostMapping("/add-topic-by-category")
    public ForumTopicDTO addTopicByCategory(HttpServletRequest request, @RequestBody @Valid ForumTopicDTO forumTopicDTO) {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        boolean isValidCategory = Arrays.stream(ForumCategoryEnum.values())
                .anyMatch(eachEnum -> eachEnum.getValue() == forumTopicDTO.getCategoryId());

        if (!validationUtils.isContainsURL(forumTopicDTO.getTitle()) &&
                !validationUtils.isContainsURL(forumTopicDTO.getContent()) && isValidCategory) {
            Optional<UserDAO> userDAO = userService.findById(userId);
            if (userDAO.isPresent()) {
                forumTopicDTO.setCreatedDate(ZonedDateTime.now());
                ForumTopicDAO forumTopicDAO = mapDTOToDAOForum.map(forumTopicDTO);
                forumTopicDAO.setByUser(userDAO.get());
                forumTopicDAO.setNumOfPosts(0L);
                forumTopicDAO.setLikes(0L);
                forumTopicService.save(forumTopicDAO);
                Optional<ForumCategoryDAO> forumCategoryDAO = forumCategoryService.findById(forumTopicDTO.getCategoryId());

                forumCategoryDAO.ifPresent(categoryDAO -> {
                    categoryDAO.setTopics(categoryDAO.getTopics() + 1);
                    categoryDAO.setPosts(categoryDAO.getPosts() + 1);
                    categoryDAO.setLastTopic(forumTopicDAO);
                    forumCategoryService.save(categoryDAO);
                });

            }
        }
        return forumTopicDTO;
    }


    @GetMapping("/get-topic-by-id/{topicId}")
    public ForumTopicDTO getTopicById(@PathVariable String topicId) throws NotFoundEntityException {
        Optional<ForumTopicDAO> forumTopicDAO = forumTopicService.findByTopicId(Long.parseLong(topicId));
        ForumTopicDTO forumTopicDTO = null;
        if (forumTopicDAO.isPresent()) {
            forumTopicDTO = mapDAOToDTOForum.map(forumTopicDAO.get());
        } else {
            logger.error(String.format("topic with topicId: %s , not found", topicId));
            throw new NotFoundEntityException(String.format("topic with topicId: %s , not found", topicId));
        }
        return forumTopicDTO;
    }

    @SecuredEndPoint
    @PostMapping("/add-post-to-topic-by-id")
    public ForumPostDTO addPostToTopicById(HttpServletRequest request, @RequestBody @Valid ForumPostDTO forumPostDTO) throws NotFoundEntityException {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        Optional<UserDAO> userDAO = userService.findById(userId);
        userDAO.orElseThrow(() -> new NotFoundEntityException(String.format("In function addPostToTopicById User with uid %s not found", userId.toString())));
        if (forumPostDTO.getTopicId() != null) {
            Optional<ForumTopicDAO> forumTopicDAO = forumTopicService.findByTopicId(forumPostDTO.getTopicId());
            forumTopicDAO.orElseThrow(() -> {
                logger.error(String.format("Not found ForumTopicDAO with id: %s", forumPostDTO.getTopicId().toString()));
                return new NotFoundEntityException(String.format("Not found ForumTopicDAO with id: %s", forumPostDTO.getTopicId().toString()));
            });

            String safeHTMLText = Jsoup.clean(forumPostDTO.getPostText(), Safelist.basic());
            forumPostDTO.setPostText(safeHTMLText);

            //Init and save Post.
            ForumPostDAO forumPostDAO = mapDTOToDAOForum.map(forumPostDTO);
            forumPostDAO.setLikes(0L);
            forumPostDAO.setCreatedDate(ZonedDateTime.now());
            forumPostDAO.setTopicId(forumTopicDAO.get().getId());
            forumPostDAO.setByUser(userDAO.get());
            forumPostDAO = forumPostService.save(forumPostDAO);

            //Add Post to Topic's posts
            forumTopicDAO.get().getPosts().add(forumPostDAO);
            forumTopicService.save(forumTopicDAO.get());

        }
        return forumPostDTO;
}
}
