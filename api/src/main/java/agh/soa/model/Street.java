package agh.soa.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="streets")
@Data
@NoArgsConstructor
public class Street implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idstreet")
    private int id;

    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "idzone")
    private Zone zone;

    @OneToMany(mappedBy = "street")
    private List<Parkometer> parkometers;

}
