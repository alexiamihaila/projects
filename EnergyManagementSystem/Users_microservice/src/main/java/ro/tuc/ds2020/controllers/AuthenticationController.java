package ro.tuc.ds2020.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ro.tuc.ds2020.dtos.UserDeviceDTO;
import ro.tuc.ds2020.entities.User;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    private static String currentUsername;
    private  RestTemplate restTemplate;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationRespone> register(
            @RequestBody RegisterRequest request
    ){
        System.out.println("aici");
        return ResponseEntity.ok(service.register(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationRespone> login(
            @RequestBody AuthenticationRequest request
    ){
        AuthenticationRespone token = service.authenticate(request);
        System.out.println(token);
        UserController.setToken(token.getToken());

        User currentUser = service.findByUsername(request.getUsername());
        UserController.setCurrentUserId(currentUser.getId_user());

        currentUsername = request.getUsername();

        return ResponseEntity.ok(token);
    }

    @GetMapping("/getUsername")
    public ResponseEntity<String> getCurrentUsername(){
        System.out.println(currentUsername);
        return ResponseEntity.ok(currentUsername);
    }
}
