package com.example.taskmanager.Service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.taskmanager.Repository.*;
import com.example.taskmanager.dto.*;
import com.example.taskmanager.model.*;
import com.example.taskmanager.LoginResponse;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private  UserRepository userRepository;

//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    public User signup(SignupRequest signupRequest) {
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        Long userId = signupRequest.getUserId();
        if (userId != null) {
            user.setId(userId);
        }
        // Save the user to the UserRepository
        User savedUser = userRepository.save(user);

        return savedUser;
    }
    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("User not found");
        }
    }
    public LoginResponse login(User user) {
        User storedUser = userRepository.findByEmail(user.getEmail());
        if (storedUser != null && storedUser.getPassword().equals(user.getPassword())) {
            // Login successful, generate a token or manage session
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUserId(storedUser.getId()); // Set the userId in the response
            return loginResponse;
        } else {
            // Authentication failed
            throw new RuntimeException("Invalid credentials");
        }
    }
}
