package com.article.hub.ServiceImpl;

import com.article.hub.DAO.ArticleRepository;
import com.article.hub.Entity.Article;
import com.article.hub.Entity.Category;
import com.article.hub.Service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;


@Service
public class ArticleServiceImpl implements ArticleService {

    Logger log = LoggerFactory.getLogger(ArticleService.class);

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public ResponseEntity<?> addNewArticle(Article article) {
        try {
            if (!Objects.isNull(article)) {
//           checkForNullValues is declared in Entity class of Article
                String errorKeyValue = article.checkForNullValues();
                if (Objects.isNull(errorKeyValue)) {
                    article.setPublication_date(new Date());
                    article.setCategory(new Category(article.getCategoryId()));
                    articleRepository.save(article);
                    return new ResponseEntity<>("{\"message\":\"Article added successfully\"}", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("{\"message\":\"Invalid value for ( " + errorKeyValue + " )\"}", HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>("{\"message\":\"Invalid Data.\"}", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("Exception in addNewArticle : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @Override
    public ResponseEntity<?> getAllArticle() {
        try {
            return new ResponseEntity<>(articleRepository.getAllArticle(null), HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exception in getAllArticle : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * the below getAllPublishedArticle API is bypassed from the JWT token so that we don't need to
     * have the token to get all the published articles. All the users can see all the published articles
     * while visiting on the website.
     */
    @Override
    public ResponseEntity<?> getAllPublishedArticle() {
        try {
            return new ResponseEntity<>(articleRepository.getAllArticle("Published"), HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exception in getAllPublishedArticle : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
