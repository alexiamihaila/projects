package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public User findUserByUsername(String username);
}
