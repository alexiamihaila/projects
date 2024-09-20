package ro.tuc.ds2020.dtos;

import lombok.Data;
import ro.tuc.ds2020.entities.Role;

@Data
public class UserDTO {
    private Integer id_user;
    private String username;
    private Role role;
    private String password;

    public UserDTO(Integer id_user, String username,Role role, String password){
        this.id_user = id_user;
        this.username = username;
        this.role = role;
        this.password = password;
    }
}
