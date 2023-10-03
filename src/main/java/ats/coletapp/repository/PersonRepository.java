package ats.coletapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ats.coletapp.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByEmail(String email);
    
}
