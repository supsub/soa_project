package agh.soa.service;

import agh.soa.dto.TicketDTO;
import agh.soa.exceptions.NoSuchParkingPlaceException;
import agh.soa.exceptions.NoSuchUserException;
import agh.soa.exceptions.PlaceAlreadyTakenException;
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
import javax.transaction.Transactional;
import java.time.Instant;
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

    @Inject
    private UserRepository userRepository;

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
        ParkingPlace parkingPlace = parkingPlaceRepository.getParkingPlaceByID(result.getParkingPlaceId());
        System.out.println("Printing parking place tickets");
        for (Ticket ticket : parkingPlace.getTickets()) {
            System.out.println(ticket);
        }



//        Ticket result = ticketRepository.buyTicket2(ticketDTO);
//        ParkingPlace parkingPlace = parkingPlaceRepository.getParkingPlaceByID(result.getParkingPlace().getId());
//        parkingPlace.setTicket(null);
//        parkingPlaceRepository.update(parkingPlace);
//        parkingPlace.setTicket(result);
//        parkingPlaceRepository.update(parkingPlace);

//         Ticket result = ticketRepository.buyTicket3(ticketDTO);
//         ParkingPlace parkingPlace = result.getParkingPlace();
//        Date expirationDate = Date.from(Instant.now().plusSeconds(ticketDTO.getDuration()));
//        ParkingPlace parkingPlace = parkingPlaceRepository.getParkingPlaceFromTicketDTO(ticketDTO);
//        if (parkingPlace.getTicket()!=null){
//            parkingPlace.setTicket(null);
//            parkingPlaceRepository.update(parkingPlace);
//        }
//        System.out.println(parkingPlace);
//        Ticket ticket = new Ticket(expirationDate,userRepository.getUserByLogin(ticketDTO.getOwner()),parkingPlace);
//        parkingPlace.setTicket(ticket);
//        parkingPlaceRepository.update(parkingPlace);
//        ticketRepository.save(ticket);

//        System.out.println("in TicketsService after ticket buy" + parkingPlaceRepository.getParkingPlaceByID(1));
//
//
//
//        orderedTickets = ticketRepository.getAllActiveTickets();
//        mostRecentTicket = orderedTickets.get(0);
//        System.out.println("Size of all active tickets: "+orderedTickets.size());
//        for (Timer timer : timerTicketExpiration.getContext().getTimerService().getAllTimers()) {
//            if (timer.getInfo()=="ticketExpirationTimer"){
//                timer.cancel();
//                }
//        }
//        long timeLeftInMillis = mostRecentTicket.getExpirationTime().getTime() - (new Date().getTime());
//        timerTicketExpiration.createTimer(timeLeftInMillis);

//
////        return new TicketDTO(parkingPlace.getId(),
////                ticketDTO.getOwner(),
////                ticketDTO.getDuration(),
////                parkingPlace.getParkometer().getStreet().getZone().getName(),
////                parkingPlace.getParkometer().getStreet().getName(),
////                parkingPlace.getParkometer().getOrdinalNumber(),
////                parkingPlace.getOrdinalNumber(), ticketDTO.getExpirationDate()
////        );
        return result;
    }

    @Override
    public Ticket getMostRecentTicket() {

        return mostRecentTicket;
    }


}
