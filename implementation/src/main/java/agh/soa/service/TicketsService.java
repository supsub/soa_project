package agh.soa.service;

import agh.soa.dto.TicketDTO;
import agh.soa.exceptions.NoSuchParkingPlaceException;
import agh.soa.exceptions.NoSuchUserException;
import agh.soa.exceptions.PlaceAlreadyTakenException;
import agh.soa.model.Ticket;
import agh.soa.repository.TicketRepository;
import agh.soa.timer.TimerTicketExpiration;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Singleton
@Startup
@Setter
@Remote(ITicketsService.class)
public class TicketsService implements ITicketsService {

    @EJB
    private TicketRepository ticketRepository ;

    @Inject
    private TimerTicketExpiration timerTicketExpiration;

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

        orderedTickets = ticketRepository.getAllActiveTickets();
        mostRecentTicket = orderedTickets.get(0);
        System.out.println("Size of all active tickets: "+orderedTickets.size());
        for (Timer allTimer : timerTicketExpiration.getContext().getTimerService().getAllTimers()) {
            allTimer.cancel();
        }
        long timeLeftInMillis = mostRecentTicket.getExpirationTime().getTime() - (new Date().getTime());
        timerTicketExpiration.createTimer(timeLeftInMillis, mostRecentTicket.getParkingPlace().getId());

        return result;
    }

    @Override
    public Ticket getMostRecentTicket() {

        return mostRecentTicket;
    }


}
