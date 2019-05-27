package agh.soa.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="parkometers")
@Data
@NoArgsConstructor
public class Parkometer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idparkometer")
    private int id;

    @Column(name = "ordinal_number")
    private int ordinalNumber;

    @ManyToOne
    @JoinColumn(name = "idstreet")
    private Street street;

    @OneToMany(mappedBy = "parkometer")
    private List<ParkingPlace> parkingPlaces;

}
