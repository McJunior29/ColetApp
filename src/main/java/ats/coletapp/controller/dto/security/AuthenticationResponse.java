package ats.coletapp.controller.dto.security;

import ats.coletapp.controller.dto.user.UserResponseDTO;

public record AuthenticationResponse(String token, UserResponseDTO user) {
}
