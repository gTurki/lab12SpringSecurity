package com.example.lab12springsecurity.Service;

import com.example.lab12springsecurity.Api.ApiException;
import com.example.lab12springsecurity.Entity.Blog;
import com.example.lab12springsecurity.Entity.User;
import com.example.lab12springsecurity.Repository.AuthRepository;
import com.example.lab12springsecurity.Repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final AuthRepository authRepository;

    // ADMIN only
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public List<Blog> getMyBlogs(Long userId) {
        User user = authRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }
        return blogRepository.findAllByUser(user);
    }

    public Blog getBlogById(Long userId, Long blogId) {
        Blog blog = blogRepository.findBlogById(blogId);

        if (blog == null) {
            throw new ApiException("Blog not found");
        }

        if (!blog.getUser().getId().equals(userId)) {
            throw new ApiException("You are not allowed to view this blog");
        }
        return blog;
    }

    public Blog getBlogByTitle(Long userId, String title) {
        User user = authRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        Blog blog = blogRepository.findBlogByTitleAndUser(title, user);

        if (blog == null) {
            throw new ApiException("Blog not found");
        }
        return blog;
    }

    public void addBlog(Long userId, Blog blog) {
        User user = authRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        if (blogRepository.findBlogByTitleAndUser(blog.getTitle(), user) != null) {
            throw new ApiException("You already have a blog with this title");
        }

        blog.setUser(user);
        blogRepository.save(blog);
    }

    public void updateBlog(Long userId, Long blogId, Blog blog) {
        Blog oldBlog = blogRepository.findBlogById(blogId);
        User user = authRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        if (oldBlog == null) {
            throw new ApiException("Blog not found");
        }

        if (!oldBlog.getUser().getId().equals(userId)) {
            throw new ApiException("You are not allowed to update this blog");
        }

        Blog sameTitle = blogRepository.findBlogByTitleAndUser(blog.getTitle(), user);
        if (sameTitle != null && !sameTitle.getId().equals(blogId)) {
            throw new ApiException("You already have a blog with this title");
        }

        oldBlog.setTitle(blog.getTitle());
        oldBlog.setBody(blog.getBody());
        blogRepository.save(oldBlog);
    }

    public void deleteBlog(Long userId, Long blogId) {
        Blog blog = blogRepository.findBlogById(blogId);
        User user = authRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        if (blog == null) {
            throw new ApiException("Blog not found");
        }

        if (!blog.getUser().getId().equals(userId)) {
            throw new ApiException("You are not allowed to delete this blog");
        }

        blogRepository.delete(blog);
    }
}
