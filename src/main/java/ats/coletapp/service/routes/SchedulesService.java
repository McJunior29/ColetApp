package ats.coletapp.service.routes;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ats.coletapp.exceptions.ResourceNotFoundException;
import ats.coletapp.model.Address;
import ats.coletapp.model.Schedules;
import ats.coletapp.repository.SchedulesRepository;

@Service
public class SchedulesService {
     private final SchedulesRepository schedulesRepository;

    @Autowired
    public SchedulesService(SchedulesRepository schedulesRepository) {
        this.schedulesRepository = schedulesRepository;
    }

    public Schedules save(Schedules schedules) {
        return schedulesRepository.save(schedules);
    }
    
    public Map<Address, List<Schedules>> groupSchedulesByAddress(List<Schedules> schedules) {
        return schedules.stream().collect(Collectors.groupingBy(schedule -> schedule.getRoutes().getAddress()));
    }

    public Schedules getSchedulesById(Long id) {
        Optional<Schedules> optionalSchedules = schedulesRepository.findById(id);
        return optionalSchedules.orElse(null);
    }

    public List<Schedules> getAllSchedules() {
        return schedulesRepository.findAll();
    }

    public Schedules updateSchedules(Long id, Schedules updatedSchedules) {
        if (schedulesRepository.existsById(id)) {
            updatedSchedules.setId(id);
            return schedulesRepository.save(updatedSchedules);
        }
        throw new ResourceNotFoundException("Schedules not found");
    }

    public void deleteSchedules(Long id) {
        schedulesRepository.deleteById(id);
    }
}
