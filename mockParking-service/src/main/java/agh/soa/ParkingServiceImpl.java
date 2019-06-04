package agh.soa;

import agh.soa.model.ParkingPlace;

import javax.jws.WebService;
import java.util.HashSet;

@WebService(endpointInterface = "agh.soa.IParkingService")
public class ParkingServiceImpl implements IParkingService{
    private HashSet occupiedParkingPlaces = new HashSet<ParkingPlace>();

    public boolean isEmpty(ParkingPlace place) {
        System.out.println(occupiedParkingPlaces.toString());
        return !occupiedParkingPlaces.contains(place);
    }

    //returns true if the place was successfully taken
    public boolean takePlace(ParkingPlace place) {
        return occupiedParkingPlaces.add(place);
    }

    //returns true if the place was successfully freed
    public boolean freePlace(ParkingPlace place) {
        return occupiedParkingPlaces.remove(place);
    }
}
