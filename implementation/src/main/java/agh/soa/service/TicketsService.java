package agh.soa.service;

import agh.soa.dto.TicketDTO;
import agh.soa.exceptions.NoSuchParkingPlaceException;
import agh.soa.exceptions.NoSuchUserException;
import agh.soa.exceptions.PlaceAlreadyTakenException;
import agh.soa.repository.TicketRepository;
import lombok.Getter;

import javax.ejb.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Stateless
@Remote(ITicketsService.class)
public class TicketsService implements ITicketsService {

    @EJB
    private TicketRepository ticketRepository ;

    private List<TicketDTO> orderedTickets;

    @Override
    public TicketDTO buyTicket(TicketDTO ticketDTO) throws NoSuchParkingPlaceException, NoSuchUserException, PlaceAlreadyTakenException {
        TicketDTO result = ticketRepository.buyTicket(ticketDTO);

        if (result!=null){
            if (orderedTickets==null){
                orderedTickets = new ArrayList<>();
                orderedTickets.add(result);
            }
            if (orderedTickets.size()==0){
                orderedTickets.add(result);
            }
            for (int i = 0; i < orderedTickets.size(); i++) {
                if (result.getExpirationDate().before(orderedTickets.get(i).getExpirationDate())){
                    orderedTickets.add(i,result);
                    break;
                }
            }

        }

        return result;
    }

    @Override
    public TicketDTO getMostRecentTicket() {
        if (orderedTickets==null){
            return null;
        }
        if (orderedTickets.size()==0){
            return null;
        }
        return orderedTickets.get(0);
    }

}
