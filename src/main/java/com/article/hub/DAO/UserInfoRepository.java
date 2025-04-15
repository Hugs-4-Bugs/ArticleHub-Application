package com.article.hub.DAO;

import com.article.hub.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> findByEmail(String email);          // why Optional? so that we can also handle the null value also

    // whenever we will call/run findByEmail ['email' is field name in entity] query it will internally use
    /** select * from appuser where email = 'prabhat@gmail.com' */
    // this 'appuser' belongs to @Entity(name = "appuser")

}
