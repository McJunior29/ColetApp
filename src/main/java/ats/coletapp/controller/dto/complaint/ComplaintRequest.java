package ats.coletapp.controller.dto.complaint;

public record ComplaintRequest(
    String photoUrl,
    String description,
    String street,
    String neighborhood,
    String number,
    String complement,
    Long userId
) {
    
}
