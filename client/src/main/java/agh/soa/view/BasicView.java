package agh.soa.view;

import agh.soa.model.Ticket;
import agh.soa.model.User;
import agh.soa.service.ITicketsService;
import agh.soa.service.IUsersService;
import lombok.Data;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean
@Data
public class BasicView {

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/UsersService")
    IUsersService usersService;

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/TicketsService")
    ITicketsService ticketsService;

    public List<User> getUsers(){
        return usersService.getUsers();
    }

    public Ticket getMostRecentTicket(){
        Ticket result = ticketsService.getMostRecentTicket();
        System.out.println(result);
        return result;
    }
}
