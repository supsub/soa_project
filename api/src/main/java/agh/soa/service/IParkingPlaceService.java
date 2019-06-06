package agh.soa.service;

import agh.soa.model.ParkingPlace;

public interface IParkingPlaceService {

    boolean changeParkingPlaceStatus(ParkingPlace parkingPlace, boolean newStatus);
}
