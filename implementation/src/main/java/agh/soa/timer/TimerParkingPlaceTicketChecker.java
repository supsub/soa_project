package agh.soa.timer;

import agh.soa.jms.INotifierSender;
import agh.soa.model.ParkingPlace;
import agh.soa.model.Ticket;
import agh.soa.repository.ParkingPlaceRepository;
import agh.soa.repository.TicketRepository;
import agh.soa.service.IParkingPlaceService;
import lombok.Getter;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Singleton
@Startup
@Getter
public class TimerParkingPlaceTicketChecker {

    @Resource
    private SessionContext context;

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/ParkingPlaceService")
    IParkingPlaceService parkingPlaceService;

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/NotifierSender")
    INotifierSender sender;

    @Inject
    ParkingPlaceRepository parkingPlaceRepository;

    @Inject
    private TicketRepository ticketRepository ;

    public void createTimer(long duration) {
        context.getTimerService().createTimer(duration+1000,"parkingPlaceTicketCheckerTimer");
    }

    @Timeout
    public void timeOutHandler() {

        for (Timer timer : context.getTimerService().getAllTimers()) {
            if (timer.getInfo()=="parkingPlaceTicketCheckerTimer") {
                timer.cancel();
            }
        }

        List<ParkingPlace> orderedParkingPlaces = parkingPlaceService.getOrderedParkingPlacesToBeChecked();
        ParkingPlace parkingPlaceToBeChecked = parkingPlaceRepository.getParkingPlaceByID(orderedParkingPlaces.get(0).getId());
        List<Ticket> activeTickets = ticketRepository.getAllActiveTicketsForParkingPlace(parkingPlaceToBeChecked.getId());

        if (activeTickets.size()==0) {
            sender.sendMessage("<<TIMEOUT>> - Parking place with id " + parkingPlaceToBeChecked.getId() + " was taken, but ticket wasn't bought");
        }
        else{
            sender.sendMessage("<<OK>> - Parking place with id " + parkingPlaceToBeChecked.getId() + " has valid ticket, everything is fine.");
        }
        if (orderedParkingPlaces.size()>1){
            long timeLeftInMillis = orderedParkingPlaces.get(1).getLastTaken().getTime()+parkingPlaceRepository.getMarginTime()*1000- (new Date().getTime());
            createTimer(timeLeftInMillis);
        }
        parkingPlaceService.popFirst();

    }
}
