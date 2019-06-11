package agh.soa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TicketDTO implements Serializable {

    private int parkingPlaceId;
    private String owner;
    private int duration;
    private String zone;
    private String street;
    private int parkometerOrd;
    private int ordinalNumber;
    private Date expirationDate;

    public TicketDTO(String zone, String street, int parkometerOrd, int ordinalNumber, Date expirationDate) {
        this.zone = zone;
        this.street = street;
        this.parkometerOrd = parkometerOrd;
        this.ordinalNumber = ordinalNumber;
        this.expirationDate = expirationDate;
    }

    public TicketDTO(int parkingPlaceId, String owner, Date expirationDate) {
        this.parkingPlaceId = parkingPlaceId;
        this.owner = owner;
        this.expirationDate = expirationDate;
    }
}
