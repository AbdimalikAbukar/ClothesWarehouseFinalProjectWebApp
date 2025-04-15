package com.assignment1.clothes.Service;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.assignment1.clothes.repository.UserRepository;

@Service
public class UDService implements UserDetailsService {

    private final UserRepository userRepository;

    public UDService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.assignment1.clothes.model.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();

    }
}
