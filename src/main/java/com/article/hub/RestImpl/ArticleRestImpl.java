package com.article.hub.RestImpl;


import com.article.hub.Entity.Article;
import com.article.hub.Rest.ArticleRest;
import com.article.hub.Service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleRestImpl implements ArticleRest {

    Logger log = LoggerFactory.getLogger(ArticleRestImpl.class);

    @Autowired
    ArticleService articleService;

    @Override
    public ResponseEntity<?> addNewArticle(Article article) {
        try {
            return articleService.addNewArticle(article);
        } catch (Exception ex) {
            log.error("Exception in addNewArticle : {}", ex.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllArticle() {
        try {
            return articleService.getAllArticle();
        } catch (Exception ex) {
            log.error("Exception in getAllArticle : {}", ex.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllPublishedArticle() {
        try {
            return articleService.getAllPublishedArticle();
        } catch (Exception ex) {
            log.error("Exception in getAllPublishedArticle : {}", ex.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> updateArticle(Article article) {
        try {
            return articleService.updateArticle(article);
        } catch (Exception ex) {
            log.error("Exception in updateArticle : {}", ex.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<?> deleteArticle(Integer id) {
        try {
           return articleService.deleteArticle(id);
        } catch (Exception ex) {
            log.error("Exception in deleteArticle : {}", ex.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @Override
//    public ResponseEntity<?> updatePaidStatus(Integer id, Boolean paid) {
//        try{
//            return articleService.updatePaidStatus(id, paid);
//        }catch(Exception ex){
//        log.error("error in updatePaidStatus {}", ex.getMessage());
//        }
//        return null;
//    }


}
