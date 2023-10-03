package ats.coletapp.controller.dto.security;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


public record RegisterPersonRequest(
        @JsonProperty("name") @NotBlank @NotEmpty String name,
        @JsonProperty("email") @NotBlank @NotEmpty @Email String email
) {
}
