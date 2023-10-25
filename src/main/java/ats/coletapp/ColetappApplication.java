package ats.coletapp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import ats.coletapp.model.Address;
import ats.coletapp.model.Complaint;
import ats.coletapp.model.Enum.DaysOfWeekEnum;
import ats.coletapp.model.Enum.PermissionTypeEnum;
import ats.coletapp.model.Enum.UnidadeFederacao;
import ats.coletapp.model.Person;
import ats.coletapp.model.Routes;
import ats.coletapp.model.Schedules;
import ats.coletapp.model.User;
import ats.coletapp.repository.AddressRepository;
import ats.coletapp.repository.ComplaintRepository;
import ats.coletapp.repository.PersonRepository;
import ats.coletapp.repository.RoutesRepository;
import ats.coletapp.repository.SchedulesRepository;
import ats.coletapp.repository.UserRepository;

@SpringBootApplication
public class ColetappApplication implements CommandLineRunner {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AddressRepository addressRespository;
    private final ComplaintRepository complaintRepository;
    private final SchedulesRepository schedulesRepository;
    private final RoutesRepository routesrRepository;

    public ColetappApplication(PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            PersonRepository personRepository,
            AddressRepository addressRespository,
            ComplaintRepository complaintRepository,
            SchedulesRepository schedulesRepository,
            RoutesRepository routesrRepository) {
        this.addressRespository = addressRespository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.complaintRepository = complaintRepository;
        this.schedulesRepository = schedulesRepository;
        this.routesrRepository = routesrRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ColetappApplication.class, args);
    }

    @Override
   public void run(String... args) throws Exception {
    if (userRepository.findAll().isEmpty()) {
        Address address = Address.builder()
                .street("Rua Romeu")
                .cep("62700-000")
                .country("Caninde")
                .neighborhood("Centro")
                .number("345")
                .unidadeFederacao(UnidadeFederacao.CEARA)
                .build();

        Address address2 = Address.builder()
                .street("Rua Ercilio")
                .cep("62700-000")
                .country("Caninde")
                .neighborhood("Campinas")
                .number("315")
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

        Routes routes = Routes.builder()
                .address(address)
                .build();

        List<DaysOfWeekEnum> daysOfWeekList = new ArrayList<>();
        daysOfWeekList.add(DaysOfWeekEnum.Segunda);

        daysOfWeekList.add(DaysOfWeekEnum.Quarta);

        daysOfWeekList.add(DaysOfWeekEnum.Sexta);

        List<Schedules> schedulesList = new ArrayList<>();

		for (DaysOfWeekEnum day : daysOfWeekList) {
			Schedules newSchedule = Schedules.builder()
					.daysOfWeek(day)
					.hours(List.of(LocalTime.parse("08:10"), LocalTime.parse("09:30")))
					.routes(routes)
					.build();
		
			schedulesList.add(newSchedule);
		}

        Routes routes2 = Routes.builder()
                .address(address2)
                .build();

        List<Schedules> schedulesList2 = new ArrayList<>();
		for (DaysOfWeekEnum day : daysOfWeekList) {
			Schedules newSchedule = Schedules.builder()
					.daysOfWeek(day)
					.hours(List.of(LocalTime.parse("08:10"), LocalTime.parse("09:30")))
					.routes(routes2)
					.build();
		
			schedulesList2.add(newSchedule);
		}

        addressRespository.save(address);
        addressRespository.save(address2);
        personRepository.save(person);
        userRepository.save(user);
        complaintRepository.save(complaint);
		routesrRepository.save(routes);
        routesrRepository.save(routes2);
        schedulesRepository.saveAll(schedulesList);
        schedulesRepository.saveAll(schedulesList2);

    }
}

}
