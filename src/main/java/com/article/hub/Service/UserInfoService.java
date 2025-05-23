package com.article.hub.Service;

import com.article.hub.Entity.AuthRequest;
import com.article.hub.Entity.UserInfo;
import org.springframework.http.ResponseEntity;


public interface UserInfoService {
    ResponseEntity<?> addNewAppuser(UserInfo userInfo);

    ResponseEntity<?> login(AuthRequest authRequest);

    ResponseEntity<?> getAllAppuser();

    ResponseEntity<?> updateUserStatus(UserInfo userInfo);

    ResponseEntity<?> checkToken();

    ResponseEntity<?> updateUser(UserInfo userInfo);
}
