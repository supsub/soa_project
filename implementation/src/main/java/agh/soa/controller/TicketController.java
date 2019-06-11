package agh.soa.controller;

import agh.soa.dto.TicketDTO;
import agh.soa.exceptions.NoSuchParkingPlaceException;
import agh.soa.exceptions.NoSuchUserException;
import agh.soa.exceptions.PlaceAlreadyTakenException;
import agh.soa.service.ITicketsService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
@Path("/ticket")
public class TicketController {

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/TicketsService")
    ITicketsService ticketService;

    @POST
    @Consumes("application/json;charset=UTF-8")
    @Produces("application/json")
    public Response buyTicket(TicketDTO ticketDTO){

        try {
            TicketDTO result = ticketService.buyTicket(ticketDTO);
            if (result!=null) {
                return Response.ok("You've just bought ticket! You have "+result.getDuration()+" seconds left.").build();
            }
        }catch (NoSuchParkingPlaceException ex){
            return Response.status(Response.Status.NOT_FOUND).entity("No such parking place!").build();
        } catch (NoSuchUserException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("No such user: "+ticketDTO.getOwner()+"!").build();
        } catch (PlaceAlreadyTakenException e) {
            return Response.status(Response.Status.CONFLICT).entity("There is ticket bought for this parking place already!").build();
        }
        return Response.status(Response.Status.NO_CONTENT).entity("Something went wrong").build();
        

    }

    @GET
    @Produces("application/json")
    public List<TicketDTO> getTicketsHistory(){
        return ticketService.getTicketsHistory();
    }

}

