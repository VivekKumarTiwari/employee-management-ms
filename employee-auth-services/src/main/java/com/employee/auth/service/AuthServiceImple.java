package com.employee.auth.service;

import com.employee.auth.dtos.AuthResponse;
import com.employee.auth.dtos.LoginRequest;
import com.employee.auth.dtos.RegisterRequest;
import com.employee.auth.entity.AppUser;
import com.employee.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AuthServiceImple implements AuthService{

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email already exists");
        }

        AppUser appUser = modelMapper.map(request, AppUser.class);

        AppUser saveAppUser = userRepository.save(appUser);

      return   AuthResponse.builder().username(saveAppUser.getUsername())
                .token(jwtService.generateToken(saveAppUser)).tokenType("Bearer").build();

    }

    @Override
    public AuthResponse login(LoginRequest request) {

        AppUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid username or password"));

        if (!passwordEncoder.matches(
                request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid username or password");
        }
        return AuthResponse.builder().username(user.getUsername())
                .token(jwtService.generateToken(user)).tokenType("Bearer").build();

    }
}
