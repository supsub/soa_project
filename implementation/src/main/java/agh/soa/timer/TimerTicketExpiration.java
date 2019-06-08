package agh.soa.timer;

import agh.soa.dto.TicketDTO;
import agh.soa.repository.TicketRepository;
import agh.soa.service.ITicketsService;
import agh.soa.service.TicketsService;
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


    public void createTimer(long duration) {
        context.getTimerService().createTimer(duration, "Hello World, you've just bought a ticket!");
    }

    @Timeout
    public void timeOutHandler() {

        System.out.println("Timeout of ticket for parking place with id: " + ticketService.getMostRecentTicket());

        for (Timer allTimer : context.getTimerService().getAllTimers()) {
            allTimer.cancel();
        }
        List<TicketDTO> allActiveTickets = ticketRepository.getAllActiveTickets();
        System.out.println("Size of all active tickets: "+allActiveTickets.size());

        if (allActiveTickets.size()==0) {
            return;
        }

        TicketDTO mostRecentTicket = allActiveTickets.get(0);
        ticketService.setMostRecentTicket(mostRecentTicket);
        long timeLeftInMillis = mostRecentTicket.getExpirationDate().getTime() - (new Date().getTime());
        createTimer(timeLeftInMillis+1000);
    }
}
