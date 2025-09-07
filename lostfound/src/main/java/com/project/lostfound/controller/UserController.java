package com.project.lostfound.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import com.project.lostfound.entity.User;
import com.project.lostfound.entity.UserRequest;
import com.project.lostfound.service.UserService;

//import java.util.List;
//import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest request) {
    	
    	
    	if (request.getPassword() == null || request.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be null or empty!");
        }
    	
    	String result = userService.registerUser(request.toUser(), request.getRole());
        return ResponseEntity.ok(result);
    }
}
