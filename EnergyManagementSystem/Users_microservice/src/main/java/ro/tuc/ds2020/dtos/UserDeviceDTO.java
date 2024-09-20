package ro.tuc.ds2020.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ro.tuc.ds2020.entities.Role;

@Data
public class UserDeviceDTO {
    private String username;
    private Role role;

    public UserDeviceDTO(String username,  Role role){
        this.username = username;
        this.role = role;
    }
}
