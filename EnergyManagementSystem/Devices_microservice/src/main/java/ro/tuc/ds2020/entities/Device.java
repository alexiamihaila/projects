package ro.tuc.ds2020.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="device")
@Data
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_device")
    private Integer id_device;

    @Column(name="description")
    private String description;

    @Column(name="address")
    private String address;

    @Column(name="consumption")
    private Integer consumption;


    @ManyToOne
    @JoinColumn(name="id_user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Device(){

    }

    public Device(String description, String address, Integer consumption, User id_user) {
        this.description = description;
        this.address = address;
        this.consumption = consumption;
        this.user = id_user;
    }
}
