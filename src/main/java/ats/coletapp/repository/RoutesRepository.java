package ats.coletapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ats.coletapp.model.Routes;

public interface RoutesRepository extends JpaRepository<Routes,Long> {
    
}
