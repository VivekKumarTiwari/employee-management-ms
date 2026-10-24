package com.employee.auth.dtos;
import lombok.Data;

@Data
public class RegisterRequest {
    private Long id;

    private String username;

    private String email;

    private String password;

    private String role = "USER";
}
