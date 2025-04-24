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

    @Override
    public ResponseEntity<?> getAllAppuser() {
        try {
            // we have created a method 'getEmail()' in the JwtAuthFilter class which will fetch and return the email
            return new ResponseEntity<>(userInfoRepository.getAllAppuser(jwtAuthFilter.getEmail()), HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exception in getAllAppUser : {}", ex.getMessage()); // this will return the exception details as well
//        log.error("Exception in getAllAppUser : {}", ex); // or we can simply return the exception
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> updateUserStatus(UserInfo userInfo) {
        try {
            if (!Objects.isNull(userInfo) && !Objects.isNull(userInfo.getId()) && !Objects.isNull(userInfo.getStatus())) {
                Integer updateCount = userInfoRepository.updateUserStatus(userInfo.getStatus(), userInfo.getId());
                if (updateCount == 0) {
                    return new ResponseEntity<>("{\"message\":\"User Id Doesn't exist.\"}", HttpStatus.BAD_REQUEST);
                } else {
                    return new ResponseEntity<>("{\"message\":\"User status updated successfully.\"}", HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("{\"message\":\"Invalid Data\"}", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            log.error("Exception in updateUserStatus : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // this method will check the token is expired or not
    @Override
    public ResponseEntity<?> checkToken() {
        return new ResponseEntity<>("{\"message\":\"true\"}", HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> updateUser(UserInfo userInfo) {
        try {
            Optional<UserInfo> optionalUserInfo = userInfoRepository.findById(userInfo.getId());
            if (!optionalUserInfo.isPresent()) {
                return new ResponseEntity<>("{\"message\":\"User not found.\"}", HttpStatus.BAD_REQUEST);
            }
            UserInfo userInfo1 = optionalUserInfo.get();
            userInfo1.setEmail(userInfo.getEmail());
            userInfo1.setName(userInfo.getName());
            userInfoRepository.save(userInfo1);
            return new ResponseEntity<>("{\"message\":\"User updated successfully.\"}", HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exception in updateUser : {}", ex.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
