package ats.coletapp.controller.dto.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ats.coletapp.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserUpdateDTO {

    @NotNull @NotEmpty
    private String login;

    public User toUser(Long id){
        User user = new User();
        user.setId(id);
        user.setLogin(this.login);

        return user;
    }
}
