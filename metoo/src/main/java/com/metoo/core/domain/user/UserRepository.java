package com.metoo.core.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * User: Zhang xiaomei
 * Date: 2016/11/24
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User u where u.email=:email")
    User findByEmail(@Param("email") String email);
}
