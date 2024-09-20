package ro.tuc.ds2020.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="User")
@Data
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_user")
    private Integer id_user;

    @Column(name="username")
    private String username;

    public User(){

    }

    public User(Integer id_user){
        this.id_user = id_user;
    }

    public User(Integer id_user, String username){
        this.id_user = id_user;
        this.username = username;
    }
    public User(String username){
        this.username = username;
    }


}
