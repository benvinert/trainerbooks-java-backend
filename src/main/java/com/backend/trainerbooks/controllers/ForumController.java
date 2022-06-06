package com.backend.trainerbooks.controllers;

import com.backend.trainerbooks.DTOS.ForumCategoryDTO;
import com.backend.trainerbooks.entitys.ForumCategoryDAO;
import com.backend.trainerbooks.mappers.IMapDAOToDTOForum;
import com.backend.trainerbooks.services.ForumCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class ForumController {

    private final IMapDAOToDTOForum mapDAOToDTOForum;
    private final ForumCategoryService forumCategoryService;

    @GetMapping("/get-all-categories")
    public List<ForumCategoryDTO> findAllCategories() {
        List<ForumCategoryDAO> forumCategoryDAOS = forumCategoryService.findAllCategories();
        return mapDAOToDTOForum.map(forumCategoryDAOS);
    }
}
