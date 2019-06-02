package agh.soa.controller;

import agh.soa.model.User;
import agh.soa.service.IUsersService;
import agh.soa.service.UsersService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@RequestScoped
@Path("/hello")
public class HelloController {

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/UsersService")
    IUsersService usersService;

    @GET
    public String sayHello(){
        return "Hello! 8)";
    }
    @GET
    @Produces("application/json")
    @Path("/users")
    public List<User> getUsers(){
        return usersService.getUsers();
    }

}
