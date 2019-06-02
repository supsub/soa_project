package agh.soa.service;

import agh.soa.model.User;
import agh.soa.repository.UserRepository;
import lombok.Getter;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import java.util.List;

@Getter
@Stateful
@Remote(IParkometersService.class)
public class ParkometersService implements IParkometersService {

}
