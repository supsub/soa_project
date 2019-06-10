package agh.soa.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "parking_places")
public class ParkingPlace implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idparking_place")
    private int id;

    @Column(name = "ordinal_number")
    private int ordinalNumber;

    @Column(name = "taken")
    private boolean taken;

    @Column(name="last_taken_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTaken;

    @ManyToOne
    @JoinColumn(name = "idparkometer")
    private Parkometer parkometer;

    @OneToMany(mappedBy = "parkingPlace",cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @Override
    public String toString() {
        return "ParkingPlace{" +
                "id=" + id +
                ", ordinalNumber=" + ordinalNumber +
                ", taken=" + taken +
                ", lastTaken=" + lastTaken +
                ", parkometerId=" + parkometer.getId()+
                ", tickets size=" + tickets.size()+
                '}';
    }

    public void add(Ticket ticket) {
        if (tickets == null){
            tickets = new ArrayList<>();
        }
        tickets.add(ticket);
        ticket.setParkingPlace(this);
    }
}
