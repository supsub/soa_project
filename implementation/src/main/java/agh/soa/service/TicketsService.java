package agh.soa.service;

import agh.soa.dto.TicketDTO;
import agh.soa.exceptions.NoSuchParkingPlaceException;
import agh.soa.exceptions.NoSuchUserException;
import agh.soa.exceptions.PlaceAlreadyTakenException;
import agh.soa.model.ParkingPlace;
import agh.soa.model.Ticket;
import agh.soa.repository.ParkingPlaceRepository;
import agh.soa.repository.TicketRepository;
import agh.soa.timer.TimerTicketExpiration;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Singleton
@Startup
@Setter
@Remote(ITicketsService.class)

public class TicketsService implements ITicketsService {

    @Inject
    private TicketRepository ticketRepository ;

    @Inject
    private TimerTicketExpiration timerTicketExpiration;

    @Inject
    private ParkingPlaceRepository parkingPlaceRepository;

    private List<Ticket> orderedTickets;

    private Ticket mostRecentTicket;

    @PostConstruct
    public void init(){
        orderedTickets = ticketRepository.getAllActiveTickets();
        System.out.println(orderedTickets);
    }

    @Override
    public TicketDTO buyTicket(TicketDTO ticketDTO) throws NoSuchParkingPlaceException, NoSuchUserException, PlaceAlreadyTakenException {
        TicketDTO result = ticketRepository.buyTicket(ticketDTO);


        System.out.println("in TicketsService after ticket buy" + parkingPlaceRepository.getParkingPlaceByID(1));



        orderedTickets = ticketRepository.getAllActiveTickets();
        mostRecentTicket = orderedTickets.get(0);
        System.out.println("Size of all active tickets: "+orderedTickets.size());
        for (Timer timer : timerTicketExpiration.getContext().getTimerService().getAllTimers()) {
            if (timer.getInfo()=="ticketExpirationTimer"){
                timer.cancel();
                }
        }
        long timeLeftInMillis = mostRecentTicket.getExpirationTime().getTime() - (new Date().getTime());
        timerTicketExpiration.createTimer(timeLeftInMillis);

        return result;
    }

    @Override
    public Ticket getMostRecentTicket() {

        return mostRecentTicket;
    }


}
