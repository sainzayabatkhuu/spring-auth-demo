package com.acme.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserRequest(@NotBlank(message = "Username is mandatory") @NotEmpty @Size(min = 3, message = "{validation.name.size.too_short}") @Size(max = 200, message = "{validation.name.size.too_long}") String username,
                          @NotBlank(message = "Password is mandatory") @NotEmpty @Size(min = 2, message = "{validation.name.size.too_short}") @Size(max = 200, message = "{validation.name.size.too_long}") String password) {
}
