package agh.soa.soapController;

import agh.soa.model.ParkingPlace;
import agh.soa.service.IUsersService;

import javax.ejb.EJB;
import javax.jws.WebService;
import java.util.HashSet;

@WebService(endpointInterface = "agh.soa.soapController.IParkingService")
public class ParkingServiceImpl implements IParkingService{
    private HashSet occupiedParkingPlaces = new HashSet<ParkingPlace>();

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/UsersService")
    IUsersService usersService;

    //returns true if the place was successfully taken
    public boolean takePlace(ParkingPlace place) {
        return occupiedParkingPlaces.add(place);
    }

    //returns true if the place was successfully freed
    public boolean freePlace(ParkingPlace place) {
        return occupiedParkingPlaces.remove(place);
    }
}
