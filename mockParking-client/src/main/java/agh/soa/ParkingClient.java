package agh.soa;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class ParkingClient {
    private IParkingService parkingService = new ParkingServiceImplService().getParkingServiceImplPort();
    private int idToBeFreed;
    private int idToBeTaken;
    private boolean takeClicked;
    private boolean freeClicked;
    private boolean isParkingPlaceSuccessfullyTaken;
    private boolean isParkingPlaceSuccessfullyFreed;

    public void freeParkingPlace() {
        this.freeClicked = true;
        this.takeClicked = false;
        isParkingPlaceSuccessfullyFreed= false;
        this.isParkingPlaceSuccessfullyFreed = this.parkingService.freePlace(idToBeFreed);
        System.out.println(this.isParkingPlaceSuccessfullyFreed);
    }

    public void takeParkingPlace() {
        this.takeClicked = true;
        this.freeClicked = false;
        isParkingPlaceSuccessfullyTaken = false;
        this.isParkingPlaceSuccessfullyTaken = this.parkingService.takePlace(idToBeTaken);
        System.out.println(this.isParkingPlaceSuccessfullyTaken);
    }

    public int getIdToBeFreed() {
        return idToBeFreed;
    }
    public void setIdToBeFreed(int idToBeFreed) {
        this.idToBeFreed = idToBeFreed;
    }

    public int getIdToBeTaken() {
        return idToBeTaken;
    }
    public void setIdToBeTaken(int idToBeTaken) {
        this.idToBeTaken = idToBeTaken;
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
}
