package agh.soa.view;

import agh.soa.dto.TicketDTO;
import agh.soa.service.IParkometersService;
import agh.soa.model.User;
import agh.soa.service.ITicketsService;
import agh.soa.service.IUsersService;
import lombok.Data;
import org.primefaces.PrimeFaces;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean
@Data
public class BasicView {

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/UsersService")
    IUsersService usersService;

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/TicketsService")
    ITicketsService ticketsService;

    private String username;
    private String password;

    public List<User> getUsers(){
        return usersService.getUsers();
    }


    public TicketDTO getMostRecentTicket(){
        TicketDTO result = ticketsService.getMostRecentTicket();
        return result;
    }

    public void changePassword(String login, String password){
        FacesMessage message = null;
        boolean result = usersService.changePassword(login, password);
        if (result==true){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully changed user's password", username);
        }
        else{
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Something went wrong", "Invalid username");
        }
        FacesContext.getCurrentInstance().addMessage("messages", message);
        PrimeFaces.current().ajax().addCallbackParam("loggedIn", result);
    }
}
