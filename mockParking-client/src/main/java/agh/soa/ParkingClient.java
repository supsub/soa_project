package agh.soa;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class ParkingClient {
    private IParkingService parkingService = new ParkingServiceImplService().getParkingServiceImplPort();
    private int ordinalNumberEmpty;
    private int ordinalNumberFree;
    private int ordinalNumberTake;
    private boolean isParkingPlaceEmpty;
    private boolean isParkingPlaceSuccessfullyTaken;
    private boolean isParkingPlaceSuccessfullyFreed;

    public void checkOccupancy() {
        isParkingPlaceEmpty = false;
        ParkingPlace pp = new ParkingPlace();
        pp.setOrdinalNumber(this.ordinalNumberEmpty);
        this.isParkingPlaceEmpty = this.parkingService.isEmpty(pp);
        System.out.println(this.isParkingPlaceEmpty);
    }

    public void freeParkingPlace() {
        isParkingPlaceSuccessfullyFreed= false;
        ParkingPlace pp = new ParkingPlace();
        pp.setOrdinalNumber(this.ordinalNumberFree);
        this.isParkingPlaceSuccessfullyFreed = this.parkingService.freePlace(pp);
        System.out.println(this.isParkingPlaceSuccessfullyFreed);

    }

    public void takeParkingPlace() {
        isParkingPlaceSuccessfullyTaken = false;
        ParkingPlace pp = new ParkingPlace();
        pp.setOrdinalNumber(this.ordinalNumberTake);
        this.isParkingPlaceSuccessfullyTaken = this.parkingService.takePlace(pp);
        System.out.println(this.isParkingPlaceSuccessfullyTaken);

    }



    public int getOrdinalNumberEmpty() {
        return ordinalNumberEmpty;
    }
    public void setOrdinalNumberEmpty(int ordinalNumberEmpty) {
        this.ordinalNumberEmpty = ordinalNumberEmpty;
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

    public boolean isParkingPlaceEmpty() {
        return isParkingPlaceEmpty;
    }
    public void setParkingPlaceEmpty(boolean parkingPlaceEmpty) {
        isParkingPlaceEmpty = parkingPlaceEmpty;
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

}
