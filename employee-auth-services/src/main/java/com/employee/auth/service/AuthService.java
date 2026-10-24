package com.employee.auth.service;

import com.employee.auth.dtos.AuthResponse;
import com.employee.auth.dtos.LoginRequest;
import com.employee.auth.dtos.RegisterRequest;

public interface AuthService {
   AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
