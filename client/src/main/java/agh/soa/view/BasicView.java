package agh.soa.view;

import agh.soa.IParkometersService;
import agh.soa.model.User;
import lombok.Data;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.util.List;

@ManagedBean
@Data
public class BasicView {

    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/ParkometersService")
    IParkometersService parkometersService;

    public List<User> getUsers(){
        return parkometersService.getUsers();
    }




}
