package com.article.hub.Service;

import com.article.hub.Entity.Category;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<?> addNewCategory(Category category);
}
