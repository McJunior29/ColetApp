package ats.coletapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ats.coletapp.model.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    
}
