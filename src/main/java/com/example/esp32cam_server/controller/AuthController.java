package com.example.esp32cam_server.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.esp32cam_server.model.User;
import com.example.esp32cam_server.repository.UserRepository;
import com.example.esp32cam_server.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // --- REGISTER ---
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> userData) {
        String username = userData.get("username");
        String password = userData.get("password");

        if (userRepository.findByUsername(username).isPresent()) {
            return Map.of("error", "Username already exists");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(newUser);

        String token = jwtUtil.generateToken(username);
        return Map.of("token", token);
    }

    // --- LOGIN ---
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> userData) {
        String username = userData.get("username");
        String password = userData.get("password");

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }

        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(Map.of("token", token));
    }

}