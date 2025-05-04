package com.article.hub.ServiceImpl;

import com.article.hub.DAO.CategoryRepository;
import com.article.hub.Entity.Category;
import com.article.hub.Filter.JwtAuthFilter;
import com.article.hub.Service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {

    Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Override
    public ResponseEntity<?> addNewCategory(Category category) {
        log.info("Inside addNewCategory");
        try {
            if (!Objects.isNull(category) && Objects.isNull(category.getId()) && !Objects.isNull(category.getName())) {
                if (!categoryRepository.existsByNameIgnoreCase(category.getName())) {
                    categoryRepository.save(category);
                    return new ResponseEntity<>("{\"message\":\"Category added successfully.\"}", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("{\"message\":\"Category already exist.\"}", HttpStatus.CONFLICT);
                }
            } else {
                return new ResponseEntity<>("{\"message\":\"Invalid Data.\"}", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            log.error("Exception in addNewCategory {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllCategory() {
//        log.info("Inside getAllCategory");
        try {
            return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exception in getAllCategory: {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> updateCategory(Category category) {
        try {
            if (!Objects.isNull(category) && !Objects.isNull(category.getId()) && !Objects.isNull(category.getName())) {
                if (!categoryRepository.existsByNameIgnoreCase(category.getName())) {
                    Integer updateCount = categoryRepository.updateCategory(category.getName(), category.getId());
                    if (updateCount == 0) {
                        return new ResponseEntity<>("{\"message\":\"Category does not found.\"}", HttpStatus.NOT_FOUND);
                    } else {
                        return new ResponseEntity<>("{\"message\":\"Category updated successfully.\"}", HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<>("{\"message\":\"Category with name ( " + category.getName() + " ) already exists.\"}", HttpStatus.CONFLICT);
                }
            }
            return new ResponseEntity<>("{\"message\":\"Invalid data.\"}", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("Exception in updateCatgory : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
