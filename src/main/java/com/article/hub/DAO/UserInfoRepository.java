package com.article.hub.DAO;

import com.article.hub.Entity.UserInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> findByEmail(String email);          // why Optional? so that we can also handle the null value also

    // whenever we will call/run findByEmail ['email' is field name in entity] query it will internally use
    /** select * from appuser where email = 'prabhat@gmail.com' */
    // this 'appuser' belongs to @Entity(name = "appuser")


    List<UserInfo> getAllAppuser(String email);


    // query to update the appUser status
    @Modifying
    @Transactional
    Integer updateUserStatus(@Param("status") String status, @Param("id") Integer id);

}
