package agh.soa.service;

import agh.soa.dto.TicketDTO;
import agh.soa.repository.TicketRepository;
import lombok.Getter;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Getter
@Stateless
@Remote(ITicketsService.class)
public class TicketsService implements ITicketsService {

    @EJB
    private TicketRepository ticketRepository ;


    @Override
    public TicketDTO buyTicket(TicketDTO ticketDTO) {
        return ticketRepository.buyTicket(ticketDTO);
    }
}
