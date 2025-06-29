package com.article.hub.Rest;


import com.article.hub.Entity.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/article")
public interface ArticleRest {

    @PostMapping(path = "/addNewArticle")
    ResponseEntity<?> addNewArticle(@RequestBody Article article);

    @GetMapping(path = "/getAllArticle")
    ResponseEntity<?> getAllArticle();

    @GetMapping(path = "/getAllPublishedArticle")
    ResponseEntity<?> getAllPublishedArticle();

    @PostMapping(path = "/updateArticle")
    ResponseEntity<?> updateArticle(@RequestBody(required = true) Article article);


    /**
     we can use either @GetMapping or @DeleteMapping, both will work
     for testing in Postman:
          if you will use @GetMapping use 'GET' http method and
          if you will use @DeleteMapping use 'DELETE' http method
     */
//    @GetMapping(path = "/deleteArticle/{id}")
    @DeleteMapping(path = "/deleteArticle/{id}")
    ResponseEntity<?> deleteArticle(@PathVariable Integer id);


    // New API endpoint to update the paid status of an article
//    @PostMapping(path = "/updatePaidStatus/{id}")
//    ResponseEntity<?> updatePaidStatus(@PathVariable Integer id, @RequestParam("paid") Boolean paid);

}
