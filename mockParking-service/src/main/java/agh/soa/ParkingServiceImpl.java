package agh.soa;

import agh.soa.model.ParkingPlace;

import javax.jws.WebService;

@WebService(endpointInterface = "agh.soa.IParkingService")
public class ParkingServiceImpl implements IParkingService{

    public boolean isEmpty(ParkingPlace place) {
        return true;
    }

    public boolean takePlace(ParkingPlace place) {
        return false;
    }

    public boolean freePlace(ParkingPlace place) {
        return false;
    }
}
