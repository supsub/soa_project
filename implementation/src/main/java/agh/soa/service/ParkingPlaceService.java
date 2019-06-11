package agh.soa.service;

import agh.soa.jms.INotifierSender;
import agh.soa.model.ParkingPlace;
import agh.soa.model.User;
import agh.soa.repository.ParkingPlaceRepository;
import agh.soa.timer.TimerParkingPlaceTicketChecker;
import lombok.Getter;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Getter
@Stateless
@Remote(IParkingPlaceService.class)
public class ParkingPlaceService implements IParkingPlaceService{

    @Inject
    private ParkingPlaceRepository parkingPlaceRepository ;

    private List<ParkingPlace> orderedParkingPlacesToBeChecked;

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/NotifierSender")
    INotifierSender sender;

    @Inject
    TimerParkingPlaceTicketChecker timerParkingPlaceTicketChecker;

    @Override
    public boolean changeParkingPlaceStatus(int ordinalNumber, int parkometerID, boolean newStatus) {
        ParkingPlace parkingPlace = parkingPlaceRepository.getParkingPlaceByLocation(ordinalNumber, parkometerID);
        if(newStatus == parkingPlace.isTaken()) return false;
        if(!newStatus){
            sender.sendMessage("<<PARKING PLACE FREED>> place freed with the id " + parkingPlace.getId());
        }
        if(newStatus){
            parkingPlace.setLastTaken(Date.from(Instant.now()));
        }
        parkingPlace.setTaken(newStatus);
        boolean result = parkingPlaceRepository.changeParkingPlaceStatus(parkingPlace, newStatus);

        orderedParkingPlacesToBeChecked = parkingPlaceRepository.getOrderedTakenParkingPlaces();
        for (Timer timer : timerParkingPlaceTicketChecker.getContext().getTimerService().getAllTimers()) {
            if (timer.getInfo()=="parkingPlaceTicketCheckerTimer") {
                timer.cancel();
            }
        }
        if (orderedParkingPlacesToBeChecked!=null && orderedParkingPlacesToBeChecked.size()!=0) {
            ParkingPlace checkedParkingPlace = orderedParkingPlacesToBeChecked.get(0);

            long timeLeftInMillis = checkedParkingPlace.getLastTaken().getTime()+parkingPlaceRepository.getMarginTime()*1000 - (new java.util.Date().getTime());
            timerParkingPlaceTicketChecker.createTimer(timeLeftInMillis);
        }
        return result;
    }

    @Override
    public void popFirst() {
        orderedParkingPlacesToBeChecked.remove(0);
    }

    @Override
    public List<ParkingPlace> getAllParkingPlaces() {
        String login =  FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        if (FacesContext.getCurrentInstance().getExternalContext().isUserInRole("Manager")) {
            return parkingPlaceRepository.getAllParkingPlaces();
        }
        else {
            return parkingPlaceRepository.getAllParkingPlacesOfWorker(login);
        }

    }
}
