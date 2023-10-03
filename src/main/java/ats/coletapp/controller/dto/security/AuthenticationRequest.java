package ats.coletapp.controller.dto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record AuthenticationRequest(@NotEmpty @Email String email, @NotBlank String password) {
}
