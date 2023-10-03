package ats.coletapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ats.coletapp.model.Address;

public interface AddressRepository extends JpaRepository<Address,Long> {
    
}
