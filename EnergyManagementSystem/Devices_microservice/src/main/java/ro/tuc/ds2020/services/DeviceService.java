package ro.tuc.ds2020.services;

import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private DeviceRepository deviceRepository;
    private UserRepository userRepository;

    public DeviceService(DeviceRepository deviceRepository, UserRepository userRepository){
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
    }

    public List<DeviceDTO> getDevices(){
        return deviceRepository.findAll()
                .stream()
                .map(devices -> DeviceBuilder.toDeviceDTO(devices))
                .collect(Collectors.toList());
    }

    public Device addDevice(DeviceDTO deviceDTO){
        Device device = DeviceBuilder.toDeviceEntity(deviceDTO);
        return deviceRepository.save(device);
    }

    public void deleteDeviceById(Integer id){
         deviceRepository.deleteById(id);
    }

    public Optional<Device> findById(Integer id){
        return deviceRepository.findById(id);
    }

    public Device updateDevice(Integer deviceId, DeviceDTO deviceDTO){
        Optional<Device> existingDevice = this.findById(deviceId);
        if (existingDevice.isPresent()) {
            Device device = existingDevice.get();
            device.setDescription(deviceDTO.getDescription());
            device.setAddress(deviceDTO.getAddress());
            device.setConsumption(deviceDTO.getConsumption());

            User user = userRepository.findById(deviceDTO.getId_user()).orElse(null);
            System.out.println("aici");
            if (user != null) {
                device.setUser(user);
                return deviceRepository.save(device);
            }
        }
        return null;
    }

    public List<DeviceDTO> getDevicesOfUser(Integer id_user){
        Optional<User> currentUser = userRepository.findById(id_user);

        if(currentUser.isPresent()) {
            List<Device> devicesOfUser = deviceRepository.findAllByUser(currentUser);
            List<DeviceDTO> devicesDTOs = devicesOfUser
                    .stream()
                    .map(devices -> DeviceBuilder.toDeviceDTO(devices))
                    .toList();

            return devicesDTOs;
        }
        return null;
    }

    public DeviceDTO getDeviceWithId(Integer deviceId){
        Optional<Device> device = deviceRepository.findById(deviceId);
        if(device.isPresent())
            return new DeviceDTO(device.get().getId_device(),
                                 device.get().getDescription(),
                                 device.get().getAddress(),
                                 device.get().getConsumption(),
                                 device.get().getUser().getId_user());
        return null;
    }
}
