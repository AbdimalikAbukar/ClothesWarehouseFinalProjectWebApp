package com.assignment1.clothes.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assignment1.clothes.model.User;
import com.assignment1.clothes.repository.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showRegistrationPage() {
        return "register";
    }

    @PostMapping
    public String register(@RequestParam String username, @RequestParam String password) {
        // Check if the user already exists
        if (userRepository.findByUsername(username).isPresent()) {
            return "redirect:/register?error"; // Redirect to registration page with error
        }

        // Create a new user and save it to the database
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password)); // Encrypt the password
        newUser.setRole(User.Role.USER.name()); // Set the role to "USER" using the enum

        userRepository.save(newUser);

        return "redirect:/login"; // Redirect to login page after successful registration

    }

}
