package agh.soa.soapController;

import agh.soa.service.IParkingPlaceService;

import javax.ejb.EJB;
import javax.jws.WebService;

@WebService(endpointInterface = "agh.soa.soapController.IParkingService")
public class ParkingServiceImpl implements IParkingService{

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/ParkingPlaceService")
    IParkingPlaceService parkingPlaceService;


    //returns true if the place was successfully taken
    public boolean takePlace(int placeID) {
        return parkingPlaceService.changeParkingPlaceStatus(placeID, true);
    }

    //returns true if the place was successfully freed
    public boolean freePlace(int placeID) {
        return parkingPlaceService.changeParkingPlaceStatus(placeID, false);
    }
}
