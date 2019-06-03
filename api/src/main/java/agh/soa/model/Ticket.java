package agh.soa.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="tickets")
@Data
@NoArgsConstructor
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idticket")
    private int id;

    @Column(name="expiration_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationTime;

    @ManyToOne
    @JoinColumn(name = "login")
    private User user;

    @OneToOne
    @JoinColumn(name = "idparking_place")
    private ParkingPlace parkingPlace;

    public Ticket(Date expirationTime, User user, ParkingPlace parkingPlace) {
        this.expirationTime = expirationTime;
        this.user = user;
        this.parkingPlace = parkingPlace;
    }
}
