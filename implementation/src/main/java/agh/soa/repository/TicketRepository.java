package agh.soa.repository;

import agh.soa.dto.TicketDTO;
import agh.soa.model.ParkingPlace;
import agh.soa.model.Ticket;
import agh.soa.model.User;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Stateless
public class TicketRepository  extends AbstractRepository{

    @Inject
    UserRepository userRepository;


    public TicketDTO buyTicket(TicketDTO ticketDTO) {

        ParkingPlace parkingPlace = null;

        Date expirationDate = Date.from(Instant.now().plusSeconds(60*ticketDTO.getDuration()));

        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("FROM ParkingPlace where ordinalNumber=:ordinalNumber and " +
                    "parkometer.ordinalNumber=:parkometer and " +
                    "parkometer.street.name=:street");
            query.setParameter("ordinalNumber",ticketDTO.getOrdinalNumber());
            query.setParameter("parkometer",ticketDTO.getParkometerOrd());
            query.setParameter("street",ticketDTO.getStreet());
            List<ParkingPlace> parkingPlaces= query.getResultList();
            if (parkingPlaces.size()==0){
                System.out.println("There is not such parking place");
                return null;
            }
            parkingPlace = parkingPlaces.get(0);

            if (parkingPlace.getTicket()!=null){
                System.out.println("Parking place has ticket already");
                return null;
            }
            User user = userRepository.getUserByLogin(ticketDTO.getOwner());
            if (user==null){
                System.out.println("No such user: "+ ticketDTO.getOwner());
                return null;
            }


            Ticket ticket = new Ticket(expirationDate,user,parkingPlace);
            parkingPlace.setTicket(ticket);
            entityManager.persist(ticket);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return new TicketDTO(parkingPlace.getId(),
                ticketDTO.getOwner(),
                ticketDTO.getDuration(),
                parkingPlace.getParkometer().getStreet().getZone().getName(),
                parkingPlace.getParkometer().getStreet().getName(),
                parkingPlace.getParkometer().getOrdinalNumber(),
                parkingPlace.getOrdinalNumber(),expirationDate
                );
    }
}
