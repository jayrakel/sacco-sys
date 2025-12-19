package com.sacco.system.modules.auth.service;

import com.sacco.system.modules.auth.dto.AuthenticationRequest;
import com.sacco.system.modules.auth.dto.AuthenticationResponse;
import com.sacco.system.modules.auth.dto.RegisterRequest;
import com.sacco.system.modules.auth.model.User;
import com.sacco.system.modules.auth.repository.UserRepository;
import com.sacco.system.modules.auth.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // Hash the password
                .role(request.getRole())
                .build();

        repository.save(user);

        var jwtToken = jwtUtil.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // This line checks the database for the user and verifies the password hash automatically
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // If we get here, the user is valid. Now generate the token.
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtUtil.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}