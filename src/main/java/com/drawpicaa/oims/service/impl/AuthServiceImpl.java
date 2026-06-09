package com.drawpicaa.oims.service.impl;

import com.drawpicaa.oims.dto.AuthLoginRequestDTO;
import com.drawpicaa.oims.dto.AuthRegisterRequestDTO;
import com.drawpicaa.oims.dto.AuthResponseDTO;
import com.drawpicaa.oims.entity.User;
import com.drawpicaa.oims.entity.UserRole;
import com.drawpicaa.oims.repository.UserRepository;
import com.drawpicaa.oims.security.CustomUserDetailsService;
import com.drawpicaa.oims.security.JwtService;
import com.drawpicaa.oims.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            CustomUserDetailsService userDetailsService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public AuthResponseDTO register(AuthRegisterRequestDTO requestDTO) {
        if (userRepository.existsByUsername(requestDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(requestDTO.getUsername());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setRole(UserRole.USER);

        User savedUser = userRepository.save(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getUsername());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponseDTO(token, savedUser.getUsername(), savedUser.getRole().name());
    }

    @Override
    public AuthResponseDTO login(AuthLoginRequestDTO requestDTO) {

        UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword());

        try {
            authenticationManager.authenticate(authToken);
        }
        catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid username or password");
        }

        User user = userRepository.findByUsername(requestDTO.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponseDTO(token, user.getUsername(), user.getRole().name());
    }
}
