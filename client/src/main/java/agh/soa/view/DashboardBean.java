package agh.soa.view;

import agh.soa.model.ParkingPlace;
import agh.soa.model.Ticket;
import agh.soa.service.IParkingPlaceService;
import agh.soa.service.ITicketsService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.text.SimpleDateFormat;
import java.util.List;

@ManagedBean
public class DashboardBean {

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/TicketsService")
    ITicketsService ticketsService;

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/ParkingPlaceService")
    IParkingPlaceService parkingPlaceService;

    public List<ParkingPlace> getParkingPlaces() {
        return this.parkingPlaceService.getAllParkingPlaces();
    }

    public String getFormattedTime(ParkingPlace place) {
        Ticket ticket;
        if((ticket = this.ticketsService.getLastTicketForParkingPlace(place.getId()) )!= null)
            return "expires: " + new SimpleDateFormat("dd-MM HH:mm").format(ticket.getExpirationTime());
        return "NO TICKET";
    }

    public String hasIllegalState(ParkingPlace place) {
        return null;
    }
}
