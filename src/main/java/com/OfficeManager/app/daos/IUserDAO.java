package com.OfficeManager.app.daos;

import com.OfficeManager.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserDAO extends JpaRepository<User, Integer> {

    @Query(
            value = "SELECT * FROM user u WHERE u.username = :username",
            nativeQuery = true
    )
    User findByUsername(String username);

}
