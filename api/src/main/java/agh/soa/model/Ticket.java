package agh.soa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "idparking_place")
    private ParkingPlace parkingPlace;

    public Ticket(Date expirationTime, User user, ParkingPlace parkingPlace) {
        this.expirationTime = expirationTime;
        this.user = user;
        this.parkingPlace = parkingPlace;
    }



    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", expirationTime=" + expirationTime +
                ", user=" + user.getLogin() +
                ", parkingPlaceId=" + parkingPlace.getId() +
                '}';
    }
}
