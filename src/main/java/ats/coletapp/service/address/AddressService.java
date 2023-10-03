package ats.coletapp.service.address;

import org.springframework.stereotype.Service;

import ats.coletapp.controller.dto.complaint.ComplaintRequest;
import ats.coletapp.controller.dto.person.PersonRequest;
import ats.coletapp.model.Address;
import ats.coletapp.repository.AddressRepository;

@Service
public class AddressService {
    private final AddressRepository addressRespository;

    AddressService(AddressRepository addressRespository){
        this.addressRespository = addressRespository;
    }

    public Address save(Address address){
        return this.addressRespository.save(address);
    }

    public Address createAddress(PersonRequest personRequest){
        Address address = Address.builder()
                          .cep(personRequest.cep())
                          .country(personRequest.country())
                          .neighborhood(personRequest.neighborhood())
                          .street(personRequest.street())
                          .number(personRequest.number())
                          .unidadeFederacao(personRequest.unidadeFederacao())
                          .build();
                          
        return this.save(address);
    }

    public Address createAddressComplaint(ComplaintRequest complaintRequest){
        Address address = Address.builder()
                          .neighborhood(complaintRequest.neighborhood())
                          .street(complaintRequest.street())
                          .number(complaintRequest.number())
                          .complement(complaintRequest.complement())
                          .build();
                          
        return this.save(address);
    }
}
