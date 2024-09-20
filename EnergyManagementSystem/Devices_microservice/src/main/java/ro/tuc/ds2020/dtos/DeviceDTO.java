package ro.tuc.ds2020.dtos;

import lombok.Data;

@Data
public class DeviceDTO {
    private Integer id_device;
    private String description;
    private String address;
    private Integer consumption;
    private Integer id_user;

    public DeviceDTO(Integer id_device, String description, String address, Integer consumption, Integer id_user) {
        this.id_device = id_device;
        this.description = description;
        this.address = address;
        this.consumption = consumption;
        this.id_user = id_user;
    }





}
