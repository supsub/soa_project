package agh.soa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login")
    private String login;

    @Column(name="passwd")
    private String password;

    @Column(name="role")
    private String role;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "idzone")
    private Zone zone;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;
}
