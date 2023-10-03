package ats.coletapp.controller.dto.person;

import org.springframework.stereotype.Component;

import ats.coletapp.model.Person;
import ats.coletapp.shared.Mapper;



@Component
public class PersonResponseMapper implements Mapper<Person, PersonResponse> {
    @Override
    public PersonResponse map(Person source) {
        return new PersonResponse(
                source.getId(),
                source.getName(),
                source.getEmail(),
                source.getAddress().getNumber(),
                source.getAddress().getCep(),
                source.getAddress().getNeighborhood(),
                source.getAddress().getStreet(),
                source.getAddress().getCountry(),
                source.getAddress().getUnidadeFederacao()
                );
    }
}
