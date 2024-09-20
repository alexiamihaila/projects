package ro.tuc.ds2020.dtos;

import lombok.Data;

@Data
public class LoginDTO {
    private Integer id_user;
    private String username;
    private String password;

    public LoginDTO(String username, String password, Integer id_user){
        this.id_user = id_user;
        this.username = username;
        this.password = password;
    }
}
