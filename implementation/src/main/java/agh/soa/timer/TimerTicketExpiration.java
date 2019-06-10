package agh.soa.timer;

import agh.soa.model.ParkingPlace;
import agh.soa.model.Ticket;
import agh.soa.repository.ParkingPlaceRepository;
import agh.soa.repository.TicketRepository;
import agh.soa.service.ITicketsService;
import lombok.Getter;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Singleton
@Startup
@Getter
public class TimerTicketExpiration {

    @Resource
    private SessionContext context;

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/TicketsService")
    ITicketsService ticketService;

    @Inject
    TicketRepository ticketRepository;

    @Inject
    private ParkingPlaceRepository parkingPlaceRepository ;

    public void createTimer(long duration) {
        context.getTimerService().createTimer(duration+1000, "ticketExpirationTimer");
    }

    @Timeout
    public void timeOutHandler(Timer allTimer) {

        ParkingPlace parkingPlace = ticketService.getMostRecentTicket().getParkingPlace();
        parkingPlace = parkingPlaceRepository.getParkingPlaceByID(parkingPlace.getId());

        if (parkingPlace.isTaken()){
            System.out.println("<<TICKET EXPIRED>> - Notify worker. There is still car in parking place of id "+parkingPlace.getId());
        }
        else{
            System.out.println("<<TICKET EXPIRED>> - Everything is fine.");
        }
        for (Timer timer : context.getTimerService().getAllTimers()) {
            if (timer.getInfo()=="ticketExpirationTimer") {
                timer.cancel();
            }
        }
        List<Ticket> allActiveTickets = ticketRepository.getAllActiveTickets();


        if (allActiveTickets.size()==0) {
            return;
        }

        Ticket mostRecentTicket = allActiveTickets.get(0);
        ticketService.setMostRecentTicket(mostRecentTicket);
        long timeLeftInMillis = mostRecentTicket.getExpirationTime().getTime() - (new Date().getTime());
        createTimer(timeLeftInMillis);
    }
}
