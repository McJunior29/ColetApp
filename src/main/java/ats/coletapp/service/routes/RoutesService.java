package ats.coletapp.service.routes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ats.coletapp.model.Routes;
import ats.coletapp.repository.RoutesRepository;

@Service
public class RoutesService {
    private final RoutesRepository routesRepository;

    @Autowired
    public RoutesService(RoutesRepository routesRepository) {
        this.routesRepository = routesRepository;
    }
  
    public Routes updateRoute(Long id, Routes updatedRoute) {
        Optional<Routes> existingRouteOptional = routesRepository.findById(id);

        if (existingRouteOptional.isPresent()) {
            Routes existingRoute = existingRouteOptional.get();
            existingRoute.setAddress(updatedRoute.getAddress());
            return routesRepository.save(existingRoute);
        } else {
            throw new RuntimeException("Route not found with id: " + id);
        }
    }

  
    public void deleteRoute(Long id) {
        routesRepository.deleteById(id);
    }

  
    public Routes getRouteById(Long id) {
        return routesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found with id: " + id));
    }

  
    public List<Routes> getAllRoutes() {
        return routesRepository.findAll();
    }
}
