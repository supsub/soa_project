package agh.soa.controller;

import agh.soa.dto.TicketDTO;
import agh.soa.service.ITicketsService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/ticket")
public class TicketController {

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/TicketsService")
    ITicketsService ticketService;

    @POST
    @Consumes("application/json;charset=UTF-8")
    @Produces("application/json")
    public Response buyTicket(TicketDTO ticketDTO){
        TicketDTO result =  ticketService.buyTicket(ticketDTO);
        if (result==null){
            return Response.status(Response.Status.NOT_FOUND).entity("Encountered problems buying ticket ").build();
        }
        return Response.ok("You've just bought ticket! You have "+result.getDuration()+" minutes left.").build();
    }

}

