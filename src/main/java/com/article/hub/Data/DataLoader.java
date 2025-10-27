package com.article.hub.Data;

import com.article.hub.DAO.UserInfoRepository;
import com.article.hub.Entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements ApplicationRunner{

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // this is the default email for admin user and can't be deleted.
        if (!userInfoRepository.findByEmail("mailtoprabhat72@gmail.com").isPresent()){
            UserInfo userInfo = new UserInfo();
            userInfo.setName("Admin");
            userInfo.setEmail("mailtoprabhat72@gmail.com");
            userInfo.setIsDeletable("false");
            userInfo.setStatus("true");
            userInfo.setPassword(encoder.encode("Prabhat12"));
            userInfoRepository.save(userInfo);
        }
        // this is the not safe way and should not be shown the username or password like this better we can send it on the email
        System.out.println("========== You can login with email: (mailtoprabhat72@gmail.com) and password (Admin name with 1st char is caps and then numeric one two) ==========");
    }
}
