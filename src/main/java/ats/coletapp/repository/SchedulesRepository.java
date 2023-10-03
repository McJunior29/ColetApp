package ats.coletapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ats.coletapp.model.Schedules;

public interface SchedulesRepository extends JpaRepository<Schedules,Long> {
    
}
