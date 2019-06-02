package agh.soa.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

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

    @ManyToOne
    @JoinColumn(name = "idparkometer")
    private Parkometer parkometer;

    @OneToOne(mappedBy = "parkingPlace")
    private Ticket ticket;
}
