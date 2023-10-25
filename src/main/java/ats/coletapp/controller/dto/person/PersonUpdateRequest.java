package ats.coletapp.controller.dto.person;

public record PersonUpdateRequest(
        Long userId,
        String name,
        String email,
        String street,
        String number,
        String neighborhood,
        String password,
        String confirmPassword
        ) {
}
