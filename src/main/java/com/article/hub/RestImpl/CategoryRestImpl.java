package com.article.hub.RestImpl;

import com.article.hub.DAO.CategoryRepository;
import com.article.hub.Entity.Category;
import com.article.hub.Filter.JwtAuthFilter;
import com.article.hub.Rest.CategoryRest;
import com.article.hub.Service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class CategoryRestImpl implements CategoryRest {

    Logger log = LoggerFactory.getLogger(CategoryRestImpl.class);

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Override
    public ResponseEntity<?> addNewCategory(Category category) {
        try {
            return categoryService.addNewCategory(category);
        } catch (Exception ex) {
            log.error("Exception in addNewCategory : {}", ex.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
