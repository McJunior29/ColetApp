package ats.coletapp.controller.dto.person;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record PersonUpdateRequest(@NotBlank @NotEmpty String name) {
}
