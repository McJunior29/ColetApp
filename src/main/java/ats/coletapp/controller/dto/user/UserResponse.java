package ats.coletapp.controller.dto.user;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponse(

    @JsonProperty("user-id")
    Long id

) {
    
}
