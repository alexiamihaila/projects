package ro.tuc.ds2020.controllers;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceRabbitDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.services.DeviceService;
import ro.tuc.ds2020.services.Producer;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class DeviceController {
    private DeviceService deviceService;
    private RestTemplate restTemplate;

    private static String token;

    private Producer producer;
    public DeviceController(DeviceService deviceService, Producer producer){
        this.deviceService = deviceService;
        this.restTemplate = new RestTemplate();
        this.producer = producer;
    }


    @GetMapping("/admin/devices/allDevices")
    public ResponseEntity<List<DeviceDTO>> allDevices(){
        System.out.println("aici");
        List<DeviceDTO> devices = deviceService.getDevices();
        if(devices != null){
            return new ResponseEntity<>(devices, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/admin/devices/addDevice")
    public ResponseEntity<String> addDevice(@RequestBody DeviceDTO deviceDTO){
        Device device = deviceService.addDevice(deviceDTO);
        if(device != null) {
            //DeviceRabbitDTO rabbitDTO = new DeviceRabbitDTO(device.getId_device(), device.getConsumption(),device.getUser().getId_user(),"addDevice");
            //producer.sendMessageToQueue(rabbitDTO);
            return new ResponseEntity<>("Device successfully added!", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Failed to create device.", HttpStatus.CREATED);
    }


    @DeleteMapping("/admin/devices/deleteDevice/{deviceId}")
    public ResponseEntity<String> deleteDevice(@PathVariable Integer deviceId){
        Optional<Device> device = deviceService.findById(deviceId);
        if(device.isPresent()){
            deviceService.deleteDeviceById(deviceId);
            //DeviceRabbitDTO rabbitDTO = new DeviceRabbitDTO(deviceId,"deleteDevice");
            //producer.sendMessageToQueue(rabbitDTO);
            return new ResponseEntity<>("Device successfully deleted!", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Device not found or could not be deleted.", HttpStatus.NOT_FOUND);

    }


    @PutMapping("/admin/devices/updateDevice/{id_device}")
    public ResponseEntity<String> updateDevice(@PathVariable Integer id_device,@RequestBody DeviceDTO deviceDTO){
        System.out.println(deviceDTO.toString());
        Device device = deviceService.updateDevice(id_device, deviceDTO);
        if(device != null) {
            //DeviceRabbitDTO rabbitDTO = new DeviceRabbitDTO(device.getId_device(),device.getConsumption(),device.getUser().getId_user(),"updateDevice");
            //producer.sendMessageToQueue(rabbitDTO);
            return new ResponseEntity<>("Device updated successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Device not found or could not be updated.", HttpStatus.NOT_FOUND);

    }


    @GetMapping("client/user/viewPersonalDevices")
    public ResponseEntity<List<DeviceDTO>> allPersonalDevices(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){

        String token = authorizationHeader.substring("Bearer ".length()).trim();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Integer> currentUserId = restTemplate.exchange(
                "http://172.30.0.5:8083/springg-demo/client/getCurrentUserId",
                HttpMethod.GET,
                requestEntity,
                Integer.class
        );

        Integer currentUserIdd = currentUserId.getBody();
        List<DeviceDTO> devices = deviceService.getDevicesOfUser(currentUserIdd);
        if(devices != null)
            return new ResponseEntity<>(devices, HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @GetMapping("/admin/getDeviceWithId/{id}")
    public ResponseEntity<DeviceDTO> getDeviceWithId(@PathVariable Integer id){
        DeviceDTO deviceDTO = deviceService.getDeviceWithId(id);
        if(deviceDTO != null)
            return new ResponseEntity<>(deviceDTO, HttpStatus.OK);
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

}
