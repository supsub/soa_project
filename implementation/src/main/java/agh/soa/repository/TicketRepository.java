package agh.soa.repository;

import agh.soa.dto.TicketDTO;
import agh.soa.exceptions.NoSuchParkingPlaceException;
import agh.soa.exceptions.NoSuchUserException;
import agh.soa.exceptions.PlaceAlreadyTakenException;
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


    public TicketDTO buyTicket(TicketDTO ticketDTO) throws NoSuchParkingPlaceException, PlaceAlreadyTakenException, NoSuchUserException {

        ParkingPlace parkingPlace = null;

        TicketDTO result = null;


        Date expirationDate = Date.from(Instant.now().plusSeconds(60*ticketDTO.getDuration()));

        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("FROM ParkingPlace where ordinalNumber=:ordinalNumber and " +
                    "parkometer.ordinalNumber=:parkometer and " +
                    "parkometer.street.name=:street");
            query.setParameter("ordinalNumber",ticketDTO.getOrdinalNumber());
            query.setParameter("parkometer",ticketDTO.getParkometerOrd());
            query.setParameter("street",ticketDTO.getStreet());
            List parkingPlaces= query.getResultList();
            if (parkingPlaces.size()==0){
                System.out.println("There is not such parking place");
                throw new NoSuchParkingPlaceException();
            }
            parkingPlace = (ParkingPlace) parkingPlaces.get(0);

            if (parkingPlace.getTicket()!=null){
                System.out.println("Parking place has ticket already");
                throw new PlaceAlreadyTakenException();
            }
            User user = userRepository.getUserByLogin(ticketDTO.getOwner());
            if (user==null){
                System.out.println("No such user: "+ ticketDTO.getOwner());
                throw new NoSuchUserException();
            }


            Ticket ticket = new Ticket(expirationDate,user,parkingPlace);
            parkingPlace.setTicket(ticket);
            entityManager.persist(ticket);
            entityManager.getTransaction().commit();
            result = new TicketDTO(parkingPlace.getId(),
                    ticketDTO.getOwner(),
                    ticketDTO.getDuration(),
                    parkingPlace.getParkometer().getStreet().getZone().getName(),
                    parkingPlace.getParkometer().getStreet().getName(),
                    parkingPlace.getParkometer().getOrdinalNumber(),
                    parkingPlace.getOrdinalNumber(),expirationDate
            );

        } catch (NoSuchParkingPlaceException | PlaceAlreadyTakenException | NoSuchUserException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }
}
