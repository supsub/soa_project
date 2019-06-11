package agh.soa.view;

import agh.soa.model.ParkingPlace;
import agh.soa.model.Ticket;
import agh.soa.service.IParkingPlaceService;
import agh.soa.service.ITicketsService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class DashboardBean {

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/TicketsService")
    ITicketsService ticketsService;

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/ParkingPlaceService")
    IParkingPlaceService parkingPlaceService;

    private Ticket ticket;

    public List<ParkingPlace> getParkingPlaces() {
        return this.parkingPlaceService.getAllParkingPlaces();
    }

    public String getFormattedTime(ParkingPlace place) {
        if((ticket = this.ticketsService.getLastTicketForParkingPlace(place.getId()) )!= null  && place.isTaken()){
            place.getTickets().add(0, ticket);
            long relationalTime = (Date.from(Instant.now()).getTime() - (ticket.getExpirationTime().getTime()));
            if( relationalTime> 0 ){
                if(relationalTime > 60000*10) return "PLACE IS TAKEN, NO TICKET";
                return  "EXPIRED " + relationalTime/60000 + " minutes ago.";
            }
            return "expires: " + new SimpleDateFormat("dd-MM HH:mm").format(ticket.getExpirationTime());
        }
        if(place.isTaken())
            return "PLACE IS TAKEN, NO TICKET";
        return "";
    }

    public boolean isEverythingOK(ParkingPlace place) {
        if (!place.isTaken()) return true;
        //place is taken
        if(place.getTickets().size() > 0) {
            //there are some tickets
            return !Date.from(Instant.now()).after(place.getTickets().get(0).getExpirationTime());
        }else {
            //no tickets
            return false;
        }
    }

    public void polling(){}

}
