package com.article.hub.RestImpl;

import com.article.hub.Entity.AuthRequest;
import com.article.hub.Entity.UserInfo;
import com.article.hub.Rest.UserInfoRest;
import com.article.hub.Service.UserInfoService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;


@RestController
public class UserInfoRestImpl implements UserInfoRest {

    Logger log = (Logger) LoggerFactory.getLogger(UserInfoRestImpl.class);

    @Autowired
    UserInfoService userInfoService;

    @Override
    public ResponseEntity<?> addNewAppuser(UserInfo userInfo) {
        try {
            return userInfoService.addNewAppuser(userInfo);
        } catch (Exception ex) {
            log.error("Exception in addNewAppuser : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // we are returning the ResponseEntity of type 'login' which accepts AuthRequest
    @Override
    public ResponseEntity<?> login(AuthRequest authRequest) {
        try {
            return userInfoService.login(authRequest);
        } catch (Exception ex) {
            log.error("Exception in login : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
