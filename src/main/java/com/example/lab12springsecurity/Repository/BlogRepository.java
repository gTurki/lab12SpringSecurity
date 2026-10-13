package com.example.lab12springsecurity.Repository;

import com.example.lab12springsecurity.Entity.Blog;
import com.example.lab12springsecurity.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    Blog findBlogById(Long id);

    Blog findBlogByTitleAndUser(String title, User user);

    List<Blog> findAllByUser(User user);
}
