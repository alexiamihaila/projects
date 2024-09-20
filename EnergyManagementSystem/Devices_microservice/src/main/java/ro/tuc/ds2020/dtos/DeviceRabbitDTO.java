package ro.tuc.ds2020.dtos;

import lombok.Data;

@Data
public class DeviceRabbitDTO {
    private Integer id_device;
    private Integer max_consumption;
    private Integer user_id;
    private String operation;

    public DeviceRabbitDTO(Integer id_device, Integer max_consumption, Integer user_id,
                           String operation) {
        this.id_device = id_device;
        this.max_consumption = max_consumption;
        this.user_id = user_id;
        this.operation = operation;
    }
    public DeviceRabbitDTO(Integer id_device,
                           String operation) {
        this.id_device = id_device;
        this.operation = operation;
    }

}
