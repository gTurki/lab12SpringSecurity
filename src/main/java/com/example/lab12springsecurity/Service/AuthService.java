package com.example.lab12springsecurity.Service;

import com.example.lab12springsecurity.Api.ApiException;
import com.example.lab12springsecurity.Entity.User;
import com.example.lab12springsecurity.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    public void register(User user) {
        if (authRepository.findUserByUsername(user.getUsername()) != null) {
            throw new ApiException("Username already exists");

        }

        user.setRole("USER");
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        authRepository.save(user);
    }

    public List<User> getAllUsers() {
        return authRepository.findAll();
    }
}
