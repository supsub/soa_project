package agh.soa;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class ParkingClient {
    private IParkingService parkingService = new ParkingServiceImplService().getParkingServiceImplPort();
    private int ordinalNumberOfFreed;
    private int parkometerIdOfFreed;
    private boolean freeClicked;

    private int ordinalNumberOfTaken;
    private int parkometerIdOfTaken;
    private boolean takeClicked;

    private boolean isParkingPlaceSuccessfullyTaken;
    private boolean isParkingPlaceSuccessfullyFreed;

    public void freeParkingPlace() {
        this.freeClicked = true;
        this.takeClicked = false;
        isParkingPlaceSuccessfullyFreed= false;
        this.isParkingPlaceSuccessfullyFreed = this.parkingService.freePlace(ordinalNumberOfFreed, parkometerIdOfFreed);
    }

    public void takeParkingPlace() {
        this.takeClicked = true;
        this.freeClicked = false;
        isParkingPlaceSuccessfullyTaken = false;
        this.isParkingPlaceSuccessfullyTaken = this.parkingService.takePlace(ordinalNumberOfTaken, parkometerIdOfTaken);
    }

    public int getOrdinalNumberOfFreed() {
        return ordinalNumberOfFreed;
    }
    public void setOrdinalNumberOfFreed(int ordinalNumberOfFreed) {
        this.ordinalNumberOfFreed = ordinalNumberOfFreed;
    }

    public int getParkometerIdOfTaken() {
        return parkometerIdOfTaken;
    }
    public void setParkometerIdOfTaken(int parkometerIdOfTaken) {
        this.parkometerIdOfTaken = parkometerIdOfTaken;
    }

    public boolean isParkingPlaceSuccessfullyTaken() {
        return isParkingPlaceSuccessfullyTaken;
    }
    public void setParkingPlaceSuccessfullyTaken(boolean parkingPlaceSuccessfullyTaken) {
        isParkingPlaceSuccessfullyTaken = parkingPlaceSuccessfullyTaken;
    }

    public boolean isParkingPlaceSuccessfullyFreed() {
        return isParkingPlaceSuccessfullyFreed;
    }
    public void setParkingPlaceSuccessfullyFreed(boolean parkingPlaceSuccessfullyFreed) {
        isParkingPlaceSuccessfullyFreed = parkingPlaceSuccessfullyFreed;
    }

    public boolean isTakeClicked() {
        return takeClicked;
    }
    public void setTakeClicked(boolean takeClicked) {
        this.takeClicked = takeClicked;
    }

    public boolean isFreeClicked() {
        return freeClicked;
    }
    public void setFreeClicked(boolean freeClicked) {
        this.freeClicked = freeClicked;
    }

    public int getParkometerIdOfFreed() {
        return parkometerIdOfFreed;
    }
    public void setParkometerIdOfFreed(int parkometerIdOfFreed) {
        this.parkometerIdOfFreed = parkometerIdOfFreed;
    }

    public int getOrdinalNumberOfTaken() {
        return ordinalNumberOfTaken;
    }
    public void setOrdinalNumberOfTaken(int ordinalNumberOfTaken) {
        this.ordinalNumberOfTaken = ordinalNumberOfTaken;
    }
}
