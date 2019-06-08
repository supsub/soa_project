package agh.soa.service;

public interface IParkingPlaceService {

    boolean changeParkingPlaceStatus(int parkingPlaceID, int parkometerID, boolean newStatus);
}
