package ro.tuc.ds2020.dtos.builders;


import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.entities.User;

public class UserBuilder {
    private UserBuilder(){

    }

    public static UserDTO toUserDTO(User user){
        return new UserDTO(user.getId_user(),user.getUsername(), user.getRole(), user.getPassword());
    }

    public static User toEntity(Integer user_id, UserDTO userDTO){
        return new User(user_id, userDTO.getUsername(),userDTO.getRole(),userDTO.getPassword());
    }
}
