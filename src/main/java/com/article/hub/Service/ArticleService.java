package com.article.hub.Service;

import com.article.hub.Entity.Article;
import org.springframework.http.ResponseEntity;

public interface ArticleService {
    ResponseEntity<?> addNewArticle(Article article);

    ResponseEntity<?> getAllArticle();

    ResponseEntity<?> getAllPublishedArticle();

    ResponseEntity<?> updateArticle(Article article);

    ResponseEntity<?> deleteArticle(Integer id);
}
