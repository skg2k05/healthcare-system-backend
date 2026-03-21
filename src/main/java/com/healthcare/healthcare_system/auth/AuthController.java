package com.healthcare.healthcare_system.auth;

import com.healthcare.healthcare_system.dto.AuthResponse;
import com.healthcare.healthcare_system.dto.LoginRequest;
import com.healthcare.healthcare_system.dto.RegisterRequest;
import com.healthcare.healthcare_system.exception.EmailAlreadyExistsException;
import com.healthcare.healthcare_system.model.User;
import com.healthcare.healthcare_system.repository.UserRepository;
import com.healthcare.healthcare_system.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            boolean isPasswordValid;

            // Handles both encoded and old plain-text passwords
            if (user.getPassword() != null && user.getPassword().startsWith("$2")) {
                isPasswordValid = passwordEncoder.matches(request.getPassword(), user.getPassword());
            } else {
                isPasswordValid = user.getPassword().equals(request.getPassword());
            }

            if (!isPasswordValid) {
                return ResponseEntity.status(401).body("Invalid password");
            }

            String token = jwtUtil.generateToken(
                    user.getEmail(),
                    user.getRole()
            );

            return ResponseEntity.ok(new AuthResponse(
                    token,
                    user.getEmail(),
                    user.getRole()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // REGISTER (CITIZEN ONLY)
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new EmailAlreadyExistsException("Email already registered");
            }

            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());

            // Always encode password before saving
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole("CITIZEN");

            userRepository.save(user);

            String token = jwtUtil.generateToken(
                    user.getEmail(),
                    user.getRole()
            );

            return ResponseEntity.ok(new AuthResponse(
                    token,
                    user.getEmail(),
                    user.getRole()
            ));

        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}