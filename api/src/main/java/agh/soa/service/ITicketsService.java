package agh.soa.service;

import agh.soa.dto.TicketDTO;
import agh.soa.exceptions.NoSuchParkingPlaceException;
import agh.soa.exceptions.NoSuchUserException;
import agh.soa.exceptions.PlaceAlreadyTakenException;
import agh.soa.model.Ticket;

import java.util.Deque;
import java.util.List;

public interface ITicketsService {

    TicketDTO buyTicket(TicketDTO ticketDTO) throws NoSuchParkingPlaceException, NoSuchUserException, PlaceAlreadyTakenException;

    TicketDTO getMostRecentTicket();

    List<TicketDTO> getOrderedTickets();

    void setMostRecentTicket(TicketDTO mostRecentTicket);
}
