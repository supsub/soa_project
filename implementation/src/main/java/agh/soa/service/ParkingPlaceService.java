package agh.soa.service;

import agh.soa.model.ParkingPlace;
import agh.soa.repository.ParkingPlaceRepository;
import lombok.Getter;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.sql.Date;
import java.time.Instant;

@Getter
@Stateless
@Remote(IParkingPlaceService.class)
public class ParkingPlaceService implements IParkingPlaceService{

    @EJB
    private ParkingPlaceRepository parkingPlaceRepository ;

    @Override
    public boolean changeParkingPlaceStatus(int parkingPlaceID, boolean newStatus) {
        ParkingPlace parkingPlace = parkingPlaceRepository.getParkingPlaceByID(parkingPlaceID);
        if(newStatus){
            parkingPlace.setLastTaken(Date.from(Instant.now()));
        }
        parkingPlace.setTaken(newStatus);
        return parkingPlaceRepository.changeParkingPlaceStatus(parkingPlace, newStatus);
    }
}
