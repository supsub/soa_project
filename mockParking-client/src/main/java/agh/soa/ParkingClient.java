package agh.soa;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class ParkingClient {
    private IParkingService parkingService = new ParkingServiceImplService().getParkingServiceImplPort();
    private int ordinalNumberFree;
    private int ordinalNumberTake;
    private boolean takeClicked;
    private boolean freeClicked;
    private boolean isParkingPlaceSuccessfullyTaken;
    private boolean isParkingPlaceSuccessfullyFreed;

    public void freeParkingPlace() {
        this.freeClicked = true;
        this.takeClicked = false;
        isParkingPlaceSuccessfullyFreed= false;
        ParkingPlace pp = new ParkingPlace();
        pp.setOrdinalNumber(this.ordinalNumberFree);
        this.isParkingPlaceSuccessfullyFreed = this.parkingService.freePlace(pp);
        System.out.println(this.isParkingPlaceSuccessfullyFreed);
    }

    public void takeParkingPlace() {
        this.takeClicked = true;
        this.freeClicked = false;
        isParkingPlaceSuccessfullyTaken = false;
        ParkingPlace pp = new ParkingPlace();
        pp.setOrdinalNumber(this.ordinalNumberTake);
        this.isParkingPlaceSuccessfullyTaken = this.parkingService.takePlace(pp);
        System.out.println(this.isParkingPlaceSuccessfullyTaken);
    }

    public int getOrdinalNumberFree() {
        return ordinalNumberFree;
    }
    public void setOrdinalNumberFree(int ordinalNumberFree) {
        this.ordinalNumberFree = ordinalNumberFree;
    }

    public int getOrdinalNumberTake() {
        return ordinalNumberTake;
    }
    public void setOrdinalNumberTake(int ordinalNumberTake) {
        this.ordinalNumberTake = ordinalNumberTake;
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
