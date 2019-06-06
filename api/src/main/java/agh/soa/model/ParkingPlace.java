package agh.soa.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    @OneToOne(mappedBy = "parkingPlace")
    private Ticket ticket;

}
