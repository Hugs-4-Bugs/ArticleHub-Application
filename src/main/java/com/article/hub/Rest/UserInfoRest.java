package com.article.hub.Rest;


import com.article.hub.Entity.AuthRequest;
import com.article.hub.Entity.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/appUser")
public interface UserInfoRest {

    @PostMapping(path = "/addNewAppUser")
    ResponseEntity<?> addNewAppuser(@RequestBody(required = true)UserInfo userInfo);

    @PostMapping(path = "/login")
    ResponseEntity<?> login(@RequestBody(required = true)AuthRequest authRequest);

    @GetMapping(path = "/getAllAppuser")
    ResponseEntity<?> getAllAppuser();

    @PostMapping(path = "/updateUserStatus")
    ResponseEntity<?> updateUserStatus(@RequestBody(required = true) UserInfo userInfo);

    @GetMapping(path = "/checkToken")
    ResponseEntity<?> checkToken();

    @PostMapping(path = "/updateUser")
    ResponseEntity<?> updateUser(@RequestBody(required = true) UserInfo userInfo);
}
