package com.acme.auth.dto;

import com.acme.auth.validator.ValidEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Username is mandatory")
    @NotEmpty
    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 200, message = "{validation.name.size.too_long}")
    public String username;

    @NotBlank(message = "Email is mandatory")
    @Email
    @NotEmpty
    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 200, message = "{validation.name.size.too_long}")
    @ValidEmail
    public String email;

    @NotBlank(message = "Password is mandatory")
    @NotEmpty
    @Size(min = 2, message = "{validation.name.size.too_short}")
    @Size(max = 200, message = "{validation.name.size.too_long}")
    public String password;

    @NotEmpty
    public String role;
}
