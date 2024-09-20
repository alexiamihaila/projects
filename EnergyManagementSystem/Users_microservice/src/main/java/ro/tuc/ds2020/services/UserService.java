package ro.tuc.ds2020.services;


import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.LoginDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static Integer currentUserId;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserDTO> getUsers(){
        return userRepository.findAll()
                        .stream()
                        .map(users -> new UserDTO(
                                users.getId_user(),
                                users.getUsername(),
                                users.getRole(),
                                users.getPassword()))
                .collect(Collectors.toList());
    }

    public User addUser(User user) {
        System.out.println(user.toString());
        return userRepository.save(user);
    }

    public void deleteUser(Integer id){
         userRepository.deleteById(id);
    }

    public User findByUsername(String username){
       return userRepository.findUserByUsername(username);
    }

    public User updateUser(Integer id, UserDTO updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            User user = UserBuilder.toEntity(id,updatedUser);
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    public String loginUser(LoginDTO loginUser){
        String logUsername = loginUser.getUsername();
        String logPassword = loginUser.getPassword();

        User searchUser = userRepository.findUserByUsername(logUsername);
        if(searchUser != null){

            String correctPassword = searchUser.getPassword();

            if(correctPassword.equals(logPassword)) {
                currentUserId = searchUser.getId_user();

                return searchUser.getRole().toString();
            }

            return "Incorrect username or password!";

        }

        return "Incorrect username or password!";

    }

    public Integer getCurrentUserId(){
        return currentUserId;
    }

    public Optional<User> findUserById(Integer id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user;
        }
        return null;
    }

    public UserDTO getUserWithId(Integer deviceId){
        Optional<User> user = userRepository.findById(deviceId);
        if(user.isPresent())
            return new UserDTO(user.get().getId_user(),
                    user.get().getUsername(),
                    user.get().getRole(),
                    user.get().getPassword());
        return null;
    }



}
