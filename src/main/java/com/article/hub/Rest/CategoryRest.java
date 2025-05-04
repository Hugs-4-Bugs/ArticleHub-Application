package com.article.hub.Rest;


import com.article.hub.Entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/category")
public interface CategoryRest {

    @PostMapping(path = "addNewCategory")
    ResponseEntity<?> addNewCategory(@RequestBody(required = true) Category category);

    @GetMapping(path = "/getAllCategory")
    ResponseEntity<?> getAllCategory();

    @PostMapping(path = "/updateCategory")
    ResponseEntity<?> updateCategory(@RequestBody(required = true) Category category);
}
