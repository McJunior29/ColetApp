package ats.coletapp.controller.dto.user;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRecoveryPasswordDTO {
    Long userId;
    String password;
    String secondPassword;
}
