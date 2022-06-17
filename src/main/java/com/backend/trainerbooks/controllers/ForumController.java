package com.backend.trainerbooks.controllers;

import com.backend.trainerbooks.DTOS.ForumCategoryDTO;
import com.backend.trainerbooks.DTOS.ForumPostDTO;
import com.backend.trainerbooks.DTOS.ForumTopicDTO;
import com.backend.trainerbooks.annotations.SecuredEndPoint;
import com.backend.trainerbooks.entitys.ForumCategoryDAO;
import com.backend.trainerbooks.entitys.ForumPostDAO;
import com.backend.trainerbooks.entitys.ForumTopicDAO;
import com.backend.trainerbooks.entitys.LikeDAO;
import com.backend.trainerbooks.entitys.UserDAO;
import com.backend.trainerbooks.enums.LikeEnum;
import com.backend.trainerbooks.exceptions.NotFoundEntityException;
import com.backend.trainerbooks.jwt.JWTUtils;
import com.backend.trainerbooks.mappers.DAOToDTO.IMapDAOToDTOForum;
import com.backend.trainerbooks.mappers.DTOToDAO.IMapDTOToDAOForum;
import com.backend.trainerbooks.services.ForumCategoryService;
import com.backend.trainerbooks.services.ForumPostService;
import com.backend.trainerbooks.services.ForumTopicService;
import com.backend.trainerbooks.services.NativeQueryService;
import com.backend.trainerbooks.services.UserService;
import com.backend.trainerbooks.utils.ForumUtils;
import com.backend.trainerbooks.utils.LikeUtils;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.backend.trainerbooks.enums.JWTEnum.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
@CrossOrigin(origins = "http://localhost:3000")
public class ForumController {
    Logger logger = LoggerFactory.getLogger(ForumController.class);

    private final IMapDTOToDAOForum mapDTOToDAOForum;
    private final IMapDAOToDTOForum mapDAOToDTOForum;
    private final ForumCategoryService forumCategoryService;
    private final ForumTopicService forumTopicService;
    private final ForumPostService forumPostService;
    private final JWTUtils jwtUtils;
    private final UserService userService;
    private final LikeUtils likeUtils;
    private final NativeQueryService nativeQueryService;
    private final ForumUtils forumUtils;

    @GetMapping("/get-all-categories")
    public List<ForumCategoryDTO> findAllCategories() {
        List<ForumCategoryDAO> forumCategoryDAOS = forumCategoryService.findAllCategories();
        return mapDAOToDTOForum.map(forumCategoryDAOS);
    }

    @GetMapping("/get-all-topics-by-category-url/{categoryUrl}/{page}/{size}")
    public Map<String, Object> getAllTopicsByUrl(@PathVariable String categoryUrl, String tag,@PathVariable Integer page,@PathVariable Integer size) {
        ForumCategoryDAO forumCategoryDAO = forumCategoryService.findByUrl(categoryUrl);
        List<ForumTopicDAO> forumTopicDAOS = new LinkedList<>();
        Pageable pageable = PageRequest.of(0,5);
        if(page != null && size != null) {
            pageable = PageRequest.of(page,size);
        }
        try {
            if (tag != null) {
                forumTopicDAOS = forumTopicService.findAllForumTopicsByCategoryIdAndTagContainsOrderByDate(forumCategoryDAO.getId(), tag.toLowerCase());
            } else {
                forumTopicDAOS = forumTopicService.findAllForumTopicsByCategoryIdAndOrderLastPostCreatedDate(forumCategoryDAO.getId(),pageable);
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

        if (forumUtils.addTopicValidation(forumTopicDTO)) {
            Optional<UserDAO> userDAO = userService.findById(userId);
            if (userDAO.isPresent()) {
                forumTopicDTO.setCreatedDate(ZonedDateTime.now());
                ForumTopicDAO forumTopicDAO = mapDTOToDAOForum.map(forumTopicDTO);
                forumTopicDAO.setByUser(userDAO.get());
                forumTopicDAO.setNumOfPosts(0L);
                forumTopicDAO.setLikesCounter(0L);
                forumTopicDAO.setUsersLikes(new LinkedList<>());
                ForumPostDAO forumPostDAO = new ForumPostDAO();
                forumPostDAO.setCreatedDate(ZonedDateTime.now());
                forumTopicDAO.setLastPost(forumPostDAO);
                forumTopicService.save(forumTopicDAO);
                Optional<ForumCategoryDAO> forumCategoryDAO = forumCategoryService.findById(forumTopicDTO.getCategoryId());

                forumCategoryDAO.ifPresent(categoryDAO -> {
                    categoryDAO.setTopics(categoryDAO.getTopics() + 1);
                    categoryDAO.setLastTopic(forumTopicDAO);
                    forumCategoryService.save(categoryDAO);
                });

            }
        } else {
            logger.warn(String.format("Cannot add topic because it's not valid with current forumTopicDTO : %s  , by userId: %s",forumTopicDTO,userId));
        }
        return forumTopicDTO;
    }


    @GetMapping("/get-topic-by-id/{topicId}")
    public ForumTopicDTO getTopicById(@PathVariable String topicId) throws NotFoundEntityException {
        Optional<ForumTopicDAO> forumTopicDAO = forumTopicService.findByTopicId(Long.parseLong(topicId));
        ForumTopicDTO forumTopicDTO = null;
        if (forumTopicDAO.isPresent()) {
            forumTopicDTO = mapDAOToDTOForum.map(forumTopicDAO.get());
            Collections.reverse(forumTopicDTO.getPosts());
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
            forumPostDAO.setLikesCounter(0L);
            forumPostDAO.setCreatedDate(ZonedDateTime.now());
            forumPostDAO.setTopicId(forumTopicDAO.get().getId());
            forumPostDAO.setByUser(userDAO.get());
            if(forumPostDTO.getQuotePost() != null) {
                ForumPostDAO quotePost = new ForumPostDAO();
                quotePost.setPostText(forumPostDTO.getQuotePost().getPostText());
                UserDAO quoteUserDAO = new UserDAO();
                quoteUserDAO.setId(forumPostDTO.getQuotePost().getByUser().getId());
                quoteUserDAO.setUsername(forumPostDTO.getQuotePost().getByUser().getUsername());
                quotePost.setByUser(quoteUserDAO);
                quotePost.setId(forumPostDTO.getQuotePost().getId());
                forumPostDAO.setQuotePost(quotePost);
            }
            forumPostDAO = forumPostService.save(forumPostDAO);

            //Add Post to Topic's posts
            forumTopicDAO.get().getPosts().add(forumPostDAO);
            forumTopicDAO.get().setLastPost(forumPostDAO);
            forumTopicService.save(forumTopicDAO.get());

            Optional<ForumCategoryDAO> forumCategoryDAO = forumCategoryService.findById(forumTopicDAO.get().getCategoryId());
            forumCategoryDAO.orElseThrow(() -> {
                logger.error(String.format("Not found forumCategoryDAO with id: %s", forumTopicDAO.get().getCategoryId().toString()));
                return new NotFoundEntityException(String.format("Not found forumCategoryDAO with id: %s", forumTopicDAO.get().getCategoryId().toString()));
            });
            forumCategoryDAO.get().setLastPost(forumPostDAO);
            forumCategoryService.save(forumCategoryDAO.get());

        }
        return forumPostDTO;
    }

    @GetMapping("/get-all-posts-by-topicid/{topicId}")
    public List<ForumPostDTO> getAllPostsByTopicId(@PathVariable String topicId) throws NotFoundEntityException {
        List<ForumPostDTO> forumPostDTOS = new LinkedList<>();
        if (topicId != null) {
            Optional<ForumTopicDAO> forumTopicDAO = forumTopicService.findByTopicId(Long.parseLong(topicId));
            forumTopicDAO.orElseThrow(() -> new NotFoundEntityException(String.format("Topic Not found with Id : %s", topicId)));
            ForumTopicDTO forumTopicDTO = mapDAOToDTOForum.map(forumTopicDAO.get());
            Collections.reverse(forumTopicDTO.getPosts());
            forumPostDTOS = forumTopicDTO.getPosts();

        }
        return forumPostDTOS;
    }

    @SecuredEndPoint
    @PutMapping("/update-likes-topic")
    public ForumTopicDTO updateLikesTopic(HttpServletRequest request, @RequestBody ForumTopicDTO forumTopicDTO) {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        Optional<ForumTopicDAO> forumTopicDAOOptional = forumTopicService.findByTopicId(forumTopicDTO.getId());
        Optional<UserDAO> userDAO = userService.findById(userId);
        LikeEnum userLikeEnumClick = forumTopicDTO.getUsersLikes().get(0).getLikeEnum();
        //Validation
        forumTopicDAOOptional.orElseThrow(() -> new EntityNotFoundException(String.format("Topic Not found with Id : %s", forumTopicDTO.getId())));
        userDAO.orElseThrow(() -> new EntityNotFoundException(String.format("User Not found with Id : %s", userId)));

        ForumTopicDAO forumTopicDAO = forumTopicDAOOptional.get();
        List<LikeDAO> likeDAOS = forumTopicDAO.getUsersLikes();
        Optional<LikeDAO> userLikeDAO = likeUtils.getUserLikeIfContains(likeDAOS, userId);

        AtomicReference<Long> newCounterResult = new AtomicReference<>();
        if (userLikeDAO.isPresent()) {
            likeUtils.updateUserLikeAndCounter(forumTopicDAO, userLikeDAO.get(), userLikeEnumClick, newCounterResult, likeDAOS);
        } else {
            likeUtils.createNewLikeAndUpdateUserLikeAndCounter(forumTopicDAO, userLikeEnumClick, newCounterResult, likeDAOS, userDAO.get());
        }

        forumTopicDAO.setLikesCounter(newCounterResult.get());
        forumTopicService.save(forumTopicDAO);
        return forumTopicDTO;
    }

    @SecuredEndPoint
    @PutMapping("/update-likes-post")
    public ForumPostDTO updateLikesPost(HttpServletRequest request, @RequestBody ForumPostDTO forumPostDTO) {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        Optional<ForumPostDAO> forumPostDAOOptional = forumPostService.findByPostId(forumPostDTO.getId());
        Optional<UserDAO> userDAO = userService.findById(userId);
        LikeEnum userLikeEnumClick = forumPostDTO.getUsersLikes().get(0).getLikeEnum();

        forumPostDAOOptional.orElseThrow(() -> new EntityNotFoundException(String.format("Post Not found with Id : %s", forumPostDTO.getId())));
        userDAO.orElseThrow(() -> new EntityNotFoundException(String.format("User Not found with Id : %s", userId)));
        ForumPostDAO forumPostDAO = forumPostDAOOptional.get();
        List<LikeDAO> likeDAOS = forumPostDAO.getUsersLikes();
        Optional<LikeDAO> userLikeDAO = likeUtils.getUserLikeIfContains(likeDAOS, userId);
        AtomicReference<Long> newCounterResult = new AtomicReference<>();
        if (userLikeDAO.isPresent()) {
            likeUtils.updateUserLikeAndCounter(forumPostDAO, userLikeDAO.get(), userLikeEnumClick, newCounterResult, likeDAOS);
        } else {
            likeUtils.createNewLikeAndUpdateUserLikeAndCounter(forumPostDAO, userLikeEnumClick, newCounterResult, likeDAOS, userDAO.get());
        }
        forumPostDAO.setLikesCounter(newCounterResult.get());
        forumPostService.save(forumPostDAO);
        return forumPostDTO;
    }

    @SecuredEndPoint
    @PutMapping("/update-post")
    public ForumPostDTO updatePost(HttpServletRequest request,@RequestBody ForumPostDTO forumPostDTO) {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        Optional<ForumPostDAO> forumPostDAOOptional = forumPostService.findByPostId(forumPostDTO.getId());
        if(forumPostDAOOptional.isPresent()) {
            ForumPostDAO forumPostDAO = forumPostDAOOptional.get();
            if(Objects.equals(forumPostDAO.getByUser().getId(), userId)) {
                String safeHTMLText = Jsoup.clean(forumPostDTO.getPostText(), Safelist.basic());
                forumPostDAO.setPostText(safeHTMLText);
                forumPostService.save(forumPostDAO);
            }
        }
    return forumPostDTO;
    }

    @GetMapping("/get-hot-topics")
    public Map<Integer, List<ForumTopicDTO>> getHotTopics(){
        return nativeQueryService.getHotTopicsByCategory();
    }

}
