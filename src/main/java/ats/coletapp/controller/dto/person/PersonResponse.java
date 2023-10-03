package ats.coletapp.controller.dto.person;

import com.fasterxml.jackson.annotation.JsonProperty;

import ats.coletapp.model.Enum.UnidadeFederacao;

public record PersonResponse(
        @JsonProperty("person-id")
        Long id,
        
        String name,

        String email,

        String street,

        String number,

        String country,

        String cep,

        String neighborhood,

        UnidadeFederacao unidadeFederacao
) {
}
