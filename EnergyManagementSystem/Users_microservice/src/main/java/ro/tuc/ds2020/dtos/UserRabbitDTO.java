package ro.tuc.ds2020.dtos;

import lombok.Data;

@Data
public class UserRabbitDTO {
    private Integer user_id;
    private String operation;

    public UserRabbitDTO(Integer user_id,String operation){
        this.user_id = user_id;
        this.operation = operation;
    }
}
