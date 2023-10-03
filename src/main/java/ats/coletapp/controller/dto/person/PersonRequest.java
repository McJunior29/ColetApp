package ats.coletapp.controller.dto.person;

import ats.coletapp.model.Enum.UnidadeFederacao;

public record PersonRequest(
    String name,

    String email,

    String street,

    String number,

    String country,

    String cep,

    String neighborhood,

    String password,

    String confirmPassword,

    UnidadeFederacao unidadeFederacao
) {
    
}
