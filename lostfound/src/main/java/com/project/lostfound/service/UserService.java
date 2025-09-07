package com.project.lostfound.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.lostfound.entity.Role;
import com.project.lostfound.entity.User;
import com.project.lostfound.repository.RoleRepository;
import com.project.lostfound.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RoleRepository roleRepository;


    public String registerUser(User user, String roleName) {
    	
    	if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return "Username cannot be null or empty!";
        }
    	
        if (userRepository.existsByUsername(user.getUsername())) {
            return "User already exists!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
//        System.out.println("role: " + roleName); 
        
        Role selectedRole = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(selectedRole);
        userRepository.save(user);
        return "User registered successfully with role: " + roleName;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}