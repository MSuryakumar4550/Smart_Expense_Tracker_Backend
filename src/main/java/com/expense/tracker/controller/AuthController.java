package com.expense.tracker.controller;

import com.expense.tracker.dto.*;
import com.expense.tracker.entity.User;
import com.expense.tracker.repository.UserRepository;
import com.expense.tracker.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest()
                    .body("Email already registered!");
        }

        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .build();

        User saved = userRepository.save(user);

        String token = jwtUtil.generateToken(
                saved.getEmail(), saved.getId());

        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .userId(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody AuthRequest req) {

        User user = userRepository
                .findByEmail(req.getEmail())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(
                req.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest()
                    .body("Invalid email or password!");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(), user.getId());

        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build());
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(
            @RequestBody UpdatePasswordRequest req) {

        User user = userRepository
                .findByEmail(req.getEmail())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(
                req.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.badRequest()
                    .body("Current password is incorrect!");
        }

        user.setPassword(
                passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Password updated successfully!");
    }
}