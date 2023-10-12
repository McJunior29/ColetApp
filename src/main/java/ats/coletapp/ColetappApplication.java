package ats.coletapp;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import ats.coletapp.model.Address;
import ats.coletapp.model.Complaint;
import ats.coletapp.model.Person;
import ats.coletapp.model.User;
import ats.coletapp.model.Enum.PermissionTypeEnum;
import ats.coletapp.model.Enum.UnidadeFederacao;
import ats.coletapp.repository.AddressRepository;
import ats.coletapp.repository.ComplaintRepository;
import ats.coletapp.repository.PersonRepository;
import ats.coletapp.repository.UserRepository;

@SpringBootApplication
public class ColetappApplication implements CommandLineRunner {
	private final PersonRepository personRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final AddressRepository addressRespository;
	private final ComplaintRepository complaintRepository;

	public ColetappApplication(PasswordEncoder passwordEncoder,  UserRepository userRepository, PersonRepository personRepository,AddressRepository addressRespository, ComplaintRepository complaintRepository){
		this.addressRespository = addressRespository;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.personRepository = personRepository;
		this.complaintRepository= complaintRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ColetappApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(userRepository.findAll().isEmpty()){	
			Address address = Address.builder()
							  .street("Rua")
							  .cep("62700-000")
							  .country("Canidne")
							  .neighborhood("Centro")
							  .number("0")
							  .unidadeFederacao(UnidadeFederacao.CEARA)
							  .build();

			Person person = Person.builder()
							.name("Marcos")
							.email("marcos@teste.com")
							.address(address)
							.build();

			User user = User.builder()
				.login("marcos@teste.com")
				.person(person)
				.password(passwordEncoder.encode("123456"))
				.permission(List.of(PermissionTypeEnum.ROLE_USER))
				.build();
				
			Complaint complaint = Complaint.builder()
								.address(address)
								.createdAt(LocalDate.now())
								.description("marcss e")
								.photoUrl("../../img/img.jpg")
								.user(user)
								.build();

			
			addressRespository.save(address);
			personRepository.save(person);
			userRepository.save(user);
			complaintRepository.save(complaint);
		}					
	}

}
