package com.article.hub.Rest;


import com.article.hub.Entity.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/article")
public interface ArticleRest {

    @PostMapping(path = "/addNewArticle")
    ResponseEntity<?> addNewArticle(@RequestBody Article article);

    @GetMapping(path = "/getAllArticle")
    ResponseEntity<?> getAllArticle();

    @GetMapping(path = "/getAllPublishedArticle")
    ResponseEntity<?> getAllPublishedArticle();
}
