package ro.tuc.ds2020.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ro.tuc.ds2020.dtos.LoginDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.UserDeviceDTO;
import ro.tuc.ds2020.dtos.UserRabbitDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.services.Producer;
import ro.tuc.ds2020.services.UserService;

import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
public class UserController {
    private final UserService userService;
    private static Integer currentUserId;
    RestTemplate restTemplate;
    private Producer producer;

    private static String token;

    public UserController(UserService userService, Producer producer) {

        this.userService = userService;
        this.restTemplate = new RestTemplate();
        this.producer = producer;
    }


    @GetMapping("/admin/users/allUsers")
    public List<UserDTO> getUsers() {

        return userService.getUsers();
    }


    @PostMapping("/admin/users/addUser")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        UserDeviceDTO userDeviceDTO = new UserDeviceDTO(userDTO.getUsername(), userDTO.getRole());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<UserDeviceDTO> request = new HttpEntity<>(userDeviceDTO, headers);


        String devicesMicroserviceUrl = "http://172.30.0.2:8082/spring-demo";
        ResponseEntity<Integer> devicesResponse = restTemplate.postForEntity(
                devicesMicroserviceUrl + "/admin/devices/addUser",
                request,
                Integer.class);

        if (devicesResponse.getStatusCode() == HttpStatus.CREATED) {

            Integer user_id = devicesResponse.getBody();
            User user = UserBuilder.toEntity(user_id,userDTO);

            userService.addUser(user);
            //UserRabbitDTO userRabbitDTO = new UserRabbitDTO(user_id, "addUser");

            //producer.sendMessageToQueue(userRabbitDTO);


            return new ResponseEntity<>("User created and synchronized.", HttpStatus.CREATED);
        } else {

            return new ResponseEntity<>("Failed to create user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/admin/users/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {

        Optional<User> user = userService.findUserById(id);

        if(user.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            String devicesMicroserviceUrl = "http://172.30.0.2:8082/spring-demo/admin/deleteUser/" + id;
            ResponseEntity<Void> response = restTemplate.exchange(
                    devicesMicroserviceUrl,
                    HttpMethod.DELETE,
                    requestEntity,
                    Void.class
            );

            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {

                userService.deleteUser(id);
                //UserRabbitDTO userRabbitDTO = new UserRabbitDTO(id, "deleteUser");

                //producer.sendMessageToQueue(userRabbitDTO);

                return new ResponseEntity<>("User deleted successfully.", HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>("User not found or could not be deleted.", HttpStatus.NOT_FOUND);

    }

    @PutMapping("/admin/users/updateUsers/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody UserDTO updatedUser) {
        User updated = userService.updateUser(id, updatedUser);
        if(updated != null)
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);

        return new ResponseEntity<>("User not found or could not be updated.", HttpStatus.NOT_FOUND);
    }


   public static void setCurrentUserId(Integer id){
        UserController.currentUserId = id;
   }


    @GetMapping("/client/getCurrentUserId")
    public ResponseEntity<Integer> getCurrentUserId(){

        return new ResponseEntity<> (currentUserId, HttpStatus.OK);
    }


    @GetMapping("/admin/getUserWithId/{id}")
    public ResponseEntity<UserDTO> getDeviceWithId(@PathVariable Integer id){
        UserDTO userDto = userService.getUserWithId(id);
        if(userDto != null)
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    public static void setToken(String token) {
        UserController.token = token;
    }



}
