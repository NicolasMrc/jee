package com.efrei.repository;

import com.efrei.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface
UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
