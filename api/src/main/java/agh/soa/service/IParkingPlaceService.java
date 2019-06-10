package agh.soa.service;

import agh.soa.model.ParkingPlace;

import java.util.List;

public interface IParkingPlaceService {

    boolean changeParkingPlaceStatus(int parkingPlaceID, int parkometerID, boolean newStatus);

    List<ParkingPlace> getOrderedParkingPlacesToBeChecked();

    void popFirst();
}
