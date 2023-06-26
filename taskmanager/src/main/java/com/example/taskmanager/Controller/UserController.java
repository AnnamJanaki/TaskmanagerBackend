package com.example.taskmanager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.taskmanager.Service.UserService;
import com.example.taskmanager.model.User;
import com.example.taskmanager.dto.*;
import com.example.taskmanager.Repository.*;
@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private  UserService userService;

    @Autowired
    private  UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequest signupRequest) {
        User user = userService.signup(signupRequest);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        User storedUser = userRepository.findByEmail(user.getEmail());
        if (storedUser != null && storedUser.getPassword().equals(user.getPassword())) {
            // Login successful, generate a token or manage session

            return storedUser;
        } else {
            // Authentication failed
            throw new RuntimeException("Invalid credentials");
        }
    }

    @GetMapping("/redirect-url/{userId}")
    public ResponseEntity<String> generateRedirectUrl(@PathVariable("userId") String userId) {
        String redirectUrl = userId;
        return ResponseEntity.ok(redirectUrl);
    }



}

