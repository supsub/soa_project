package agh.soa.controller;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/ticket")
public class TicketController {

    @POST
    public void buyTicket(){

    }

}

