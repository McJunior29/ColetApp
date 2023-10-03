package ats.coletapp.service.complaint;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ats.coletapp.controller.dto.complaint.ComplaintRequest;
import ats.coletapp.exceptions.ResourceNotFoundException;

import ats.coletapp.model.Complaint;
import ats.coletapp.repository.ComplaintRepository;
import ats.coletapp.service.address.AddressService;
import ats.coletapp.service.user.UserService;

@Service
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final AddressService addressService;
    private final UserService userService;

    ComplaintService(ComplaintRepository complaintRepository, UserService userService, AddressService addressService){
        this.complaintRepository = complaintRepository;
        this.addressService = addressService;
        this.userService = userService;
    }
    public Complaint save(Complaint complaint){
        return complaintRepository.save(complaint);
    }

    public Complaint createComplaint(ComplaintRequest complaintRequest) {
        Complaint complaint = Complaint.builder()
                              .description(complaintRequest.description())
                              .photoUrl(complaintRequest.photoUrl())
                              .user(userService.searchByID(complaintRequest.userId()))
                              .createdAt(LocalDate.now())
                              .address(addressService.createAddressComplaint(complaintRequest))
                              .build();

        return this.save(complaint);
    }

    public Complaint getComplaintById(Long id) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(id);
        return optionalComplaint.orElse(null);
    }

    public List<Complaint> getAllComplaint() {
        return complaintRepository.findAll();
    }

    public Complaint updateComplaint(Long id, Complaint updatedComplaint) {
        if (complaintRepository.existsById(id)) {
            updatedComplaint.setId(id);
            return complaintRepository.save(updatedComplaint);
        }
        throw new ResourceNotFoundException("Complaint not found");
    }

    public void deleteComplaint(Long id) {
        complaintRepository.deleteById(id);
    }
}
