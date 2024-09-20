package ro.tuc.ds2020.services;

import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User addUsers(User user) throws ServerException {
        User newUser = userRepository.save(user);
        if(newUser == null){
            throw new ServerException("Save failed!");
        }
        else {
            return newUser;
        }
    }

    public boolean deleteUserById(Integer user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isPresent()) {
            userRepository.deleteById(user_id);
            return true;
        } else {
            return false;
        }
    }

    public User loadUserByUsername(String username){
        User user = userRepository.findUserByUsername(username);
        return user;
    }

}

