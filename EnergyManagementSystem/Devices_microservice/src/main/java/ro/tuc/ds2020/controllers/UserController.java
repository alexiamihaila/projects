package ro.tuc.ds2020.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.services.UserService;

import java.rmi.ServerException;
import java.util.List;

@CrossOrigin
@RestController
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/devices/allUsers")
    public ResponseEntity<List<User>>  getUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/admin/devices/addUser")
    @ResponseBody
    public ResponseEntity<Integer> addUser(@RequestBody User user){

        try {
            User newUser = userService.addUsers(user);
            return new ResponseEntity<Integer>(newUser.getId_user(), HttpStatus.CREATED);
        } catch (ServerException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/admin/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        boolean deleted = userService.deleteUserById(id);
        if (deleted) {
            return new ResponseEntity<>("Resource deleted successfully.", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Resource not found or could not be deleted.", HttpStatus.NOT_FOUND);
        }
    }



}
