package ats.coletapp.controller.dto.user;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import ats.coletapp.model.Person;
import ats.coletapp.model.User;
import ats.coletapp.model.Enum.PermissionTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponseDTO {

    private Long id;
    
    private String login;

    private List<PermissionTypeEnum> permission;

    @JsonProperty("person")
    private Person personDTO;


    public UserResponseDTO(){
    }

    public UserResponseDTO(User user){
        this.id = user.getId();
        this.login = user.getLogin();
    }

    public void setPersonDTO(Person savedPerson) {
        this.personDTO = savedPerson;
    }

}
