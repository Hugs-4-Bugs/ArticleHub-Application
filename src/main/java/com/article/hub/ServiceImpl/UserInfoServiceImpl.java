package com.article.hub.ServiceImpl;

import com.article.hub.DAO.UserInfoRepository;
import com.article.hub.Entity.AuthRequest;
import com.article.hub.Entity.UserInfo;
import com.article.hub.Filter.JwtAuthFilter;
import com.article.hub.JWT.JwtService;
import com.article.hub.JWT.UserInfoDetails;
import com.article.hub.Service.UserInfoService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;


@Service
public class UserInfoServiceImpl implements UserInfoService {

    Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtAuthFilter jwtAuthFilter;


    @Override
    public ResponseEntity<?> addNewAppuser(UserInfo userInfo) {
        log.info("Inside addNewAppuser");
        try {
            if (!validateUserInfo(userInfo))
               return new ResponseEntity<>("{\"message\":\"Missing Required Data.\"}", HttpStatus.BAD_REQUEST);
            Optional<UserInfo> db = userInfoRepository.findByEmail(userInfo.getEmail());
            if (db.isPresent())
                return new ResponseEntity<>("{\"message\":\"Email Already Exist.\"}", HttpStatus.BAD_REQUEST);
                userInfo.setPassword(encoder.encode(userInfo.getPassword()));
                userInfo.setIsDeletable("true");   // marks the object as allowed to be deleted.
                userInfo.setStatus("false");   // to set the user account status
                userInfo.setEmail(userInfo.getEmail().toLowerCase());
                userInfoRepository.save(userInfo);
               return new ResponseEntity<>("{\"message\":\"SignUp Successful\"}", HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exception in addNewAppuser : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Boolean validateUserInfo(UserInfo userInfo) {
        return !Objects.isNull(userInfo) && StringUtils.hasText(userInfo.getName())
                && StringUtils.hasText(userInfo.getEmail()) && StringUtils.hasText(userInfo.getPassword());
    }


    @Override
    public ResponseEntity<?> login(AuthRequest authRequest) {
        log.info("Inside login");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail().toLowerCase(), authRequest.getPassword()));
            if (authentication != null && authentication.isAuthenticated()) {
                /** Could not understand from here to */
                UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();  // why to typecast with UserInfoDetails
                if ("true".equalsIgnoreCase(userInfoDetails.getStatus())) {
                    return new ResponseEntity<>("{\"token\":\"" + jwtService.generateToken(authRequest.getEmail().toLowerCase()) + "\"}", HttpStatus.OK);
                    /** till here */
                } else {
                    return new ResponseEntity<>("{\"message\":\"Wait for Admin approval.\"}", HttpStatus.BAD_REQUEST);
                }
            } else {
                throw new UsernameNotFoundException("Invalid User request !");
            }
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>("{\"message\":\"Invalid Credentials.\"}", HttpStatus.UNAUTHORIZED);
        } catch (UsernameNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Exception in login : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
