package com.example.lab12springsecurity.Repository;

import com.example.lab12springsecurity.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);

    User findUserByUsername(String username);
}
