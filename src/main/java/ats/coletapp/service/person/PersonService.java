package ats.coletapp.service.person;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ats.coletapp.controller.dto.person.PersonRequest;
import ats.coletapp.controller.dto.person.PersonResponse;
import ats.coletapp.controller.dto.person.PersonResponseMapper;
import ats.coletapp.exceptions.ConflictException;
import ats.coletapp.exceptions.PersonNotFoundException;
import ats.coletapp.model.Person;
import ats.coletapp.repository.PersonRepository;
import ats.coletapp.service.address.AddressService;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonResponseMapper mapper;
    private final AddressService addressService;

    public PersonService(PersonRepository personRepository,
            PersonResponseMapper mapper,
            AddressService addressService) {
        this.personRepository = personRepository;
        this.mapper = mapper;
        this.addressService = addressService;
    }

    public PersonResponse findById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(PersonNotFoundException::new);

        return mapper.map(person);

    }
    public Person searchById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(PersonNotFoundException::new);
    }
    public List<PersonResponse> findAll(Pageable pageable) {
        return personRepository.findAll(pageable)
                .stream()
                .map(mapper::map)
                .toList();
    }
    @Transactional
    public Person save(Person person) {
        boolean personExists = personRepository.existsByEmail(person.getEmail());
        if (personExists) {
            throw new ConflictException("The person email is already being used.");
        }
        Person personSaved = personRepository.save(person);

        return personSaved;
    }
    

    public boolean existsByEmail(String Email) {
        return personRepository.existsByEmail(Email);
    }
    @Transactional
    public void deleteById(Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        }
    }

    @Transactional
    public Person savePerson(Person person) {
        return this.personRepository.save(person);
    }

    @Transactional
    public Person createdPerson(PersonRequest personRequest) {
                if (!existsByEmail(personRequest.email())) {
            Person person = new Person();
            person.setName(personRequest.name());
            person.setEmail(personRequest.email());
            person.setAddress(this.addressService.createAddress(personRequest));
    
            return this.save(person);
        } else {
            throw new ConflictException("The person Email is already being used.");
        }
    }   

}
