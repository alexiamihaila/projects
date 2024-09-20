package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;

public class DeviceBuilder {
    public DeviceBuilder(){

    }

    public static DeviceDTO toDeviceDTO(Device device){
        return new DeviceDTO(device.getId_device(), device.getDescription(), device.getAddress(), device.getConsumption(), device.getUser().getId_user());
    }

    public static Device toDeviceEntity(DeviceDTO deviceDTO){
        return new Device(deviceDTO.getDescription(), deviceDTO.getAddress(), deviceDTO.getConsumption(), new User(deviceDTO.getId_user()));
    }
}
