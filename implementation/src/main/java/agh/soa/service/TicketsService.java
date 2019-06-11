package agh.soa.service;

import agh.soa.dto.TicketDTO;
import agh.soa.exceptions.NoSuchParkingPlaceException;
import agh.soa.exceptions.NoSuchUserException;
import agh.soa.exceptions.PlaceAlreadyTakenException;
import agh.soa.jms.INotifierSender;
import agh.soa.model.ParkingPlace;
import agh.soa.model.Ticket;
import agh.soa.repository.ParkingPlaceRepository;
import agh.soa.repository.TicketRepository;
import agh.soa.repository.UserRepository;
import agh.soa.timer.TimerTicketExpiration;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;
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

    @Inject
    private UserRepository userRepository;

    private List<Ticket> orderedTickets;

    private Ticket mostRecentTicket;

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/NotifierSender")
    INotifierSender sender;

    @PostConstruct
    public void init(){
        orderedTickets = ticketRepository.getAllActiveTickets();
        System.out.println(orderedTickets);
    }

    @Override
    public TicketDTO buyTicket(TicketDTO ticketDTO) throws NoSuchParkingPlaceException, NoSuchUserException, PlaceAlreadyTakenException {


        TicketDTO result = ticketRepository.buyTicket(ticketDTO);
        ParkingPlace parkingPlace = parkingPlaceRepository.getParkingPlaceByID(result.getParkingPlaceId());
        for (Ticket ticket : parkingPlace.getTickets()) {
            System.out.println(ticket);
        }

        sender.sendMessage("<<TICKET BOUGHT>> ticket was purchased with the id " + parkingPlace.getId());


        orderedTickets = ticketRepository.getAllActiveTickets();
        mostRecentTicket = orderedTickets.get(0);
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

    @Override
    public List<Ticket> getAllActiveTicketsForParkingPlace(int parkingPlaceId){
        return ticketRepository.getAllActiveTicketsForParkingPlace(parkingPlaceId);
    }

    @Override
    public Ticket getLastTicketForParkingPlace(int parkingPlaceId){
        return ticketRepository.getLastTicketForParkingPlace(parkingPlaceId);
    }

    @Override
    public List<TicketDTO> getTicketsHistory() {
        return ticketRepository.getTicketsHistory();
    }


}
