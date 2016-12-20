package com.metoo.core.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: Zhang xiaomei
 * Date: 2016/11/24
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
