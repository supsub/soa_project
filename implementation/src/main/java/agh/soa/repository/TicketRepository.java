package agh.soa.repository;

import agh.soa.dto.TicketDTO;
import agh.soa.exceptions.NoSuchParkingPlaceException;
import agh.soa.exceptions.NoSuchUserException;
import agh.soa.exceptions.PlaceAlreadyTakenException;
import agh.soa.model.ParkingPlace;
import agh.soa.model.Ticket;
import agh.soa.model.User;
import lombok.Getter;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class TicketRepository{

//    @PersistenceContext(unitName = "NewPersistenceUnit")
//    private EntityManager entityManager;

    private EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

    @Inject
    UserRepository userRepository;

    public List<Ticket> getAllActiveTickets() {
        List<Ticket> result = new ArrayList<>();
        try {
            Query query = entityManager.createQuery("FROM Ticket order by expirationTime asc");

            result = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<TicketDTO> getAllActiveTicketDTOs() {
        List<TicketDTO> result = new ArrayList<>();
        List<Ticket> interResult;
        try {

            Query query = entityManager.createQuery("FROM Ticket where expirationTime>:now order by expirationTime asc");
            query.setParameter("now", new Date(), TemporalType.TIMESTAMP);

            interResult = query.getResultList();
            for (Ticket ticket : interResult) {
                TicketDTO ticketDTO = new TicketDTO(ticket.getParkingPlace().getId(),
                        ticket.getUser().getLogin(),
                        (int) (ticket.getExpirationTime().getTime() - new Date().getTime()) / 1000, ticket.getParkingPlace().getParkometer().getStreet().getZone().getName(),
                        ticket.getParkingPlace().getParkometer().getStreet().getName(), ticket.getParkingPlace().getParkometer().getOrdinalNumber(), ticket.getParkingPlace().getOrdinalNumber(),
                        ticket.getExpirationTime()
                );
                result.add(ticketDTO);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public TicketDTO buyTicket(TicketDTO ticketDTO) throws NoSuchParkingPlaceException, PlaceAlreadyTakenException, NoSuchUserException {

        ParkingPlace parkingPlace = null;

        TicketDTO result = null;


        Date expirationDate = Date.from(Instant.now().plusSeconds(ticketDTO.getDuration()));

        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("FROM ParkingPlace where ordinalNumber=:ordinalNumber and " +
                    "parkometer.ordinalNumber=:parkometer and " +
                    "parkometer.street.name=:street");
            query.setParameter("ordinalNumber", ticketDTO.getOrdinalNumber());
            query.setParameter("parkometer", ticketDTO.getParkometerOrd());
            query.setParameter("street", ticketDTO.getStreet());
            List parkingPlaces = query.getResultList();
            if (parkingPlaces.size() == 0) {
                System.out.println("There is not such parking place");
                throw new NoSuchParkingPlaceException();
            }
            parkingPlace = (ParkingPlace) parkingPlaces.get(0);


            if (parkingPlace.getTicket() != null &&
                    parkingPlace.getTicket().getExpirationTime().after(new Date())) {
                System.out.println("Parking place has ticket already");
                throw new PlaceAlreadyTakenException();
            }

            User user = userRepository.getUserByLogin(ticketDTO.getOwner());


            if (user == null) {
                System.out.println("No such user: " + ticketDTO.getOwner());
                throw new NoSuchUserException();
            }



            Ticket ticket = new Ticket(expirationDate, user, parkingPlace);
            System.out.println("DEBUGGING IN TICKET REPOSITORY: "+ticket);
            entityManager.persist(ticket);
            //System.out.println("LOOOOOOOOOOOOOOOOL"+ parkingPlace);
            entityManager.getTransaction().commit();
            result = new TicketDTO(parkingPlace.getId(),
                    ticketDTO.getOwner(),
                    ticketDTO.getDuration(),
                    parkingPlace.getParkometer().getStreet().getZone().getName(),
                    parkingPlace.getParkometer().getStreet().getName(),
                    parkingPlace.getParkometer().getOrdinalNumber(),
                    parkingPlace.getOrdinalNumber(), expirationDate
            );

        } catch (NoSuchParkingPlaceException | PlaceAlreadyTakenException | NoSuchUserException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    public void deleteTicketById(int id) {
        try{

            Query query = entityManager.createQuery("FROM Ticket where id=:id");
            query.setParameter("id",id);
            Ticket ticket = (Ticket) query.getSingleResult();
            ParkingPlace parkingPlace = ticket.getParkingPlace();
            parkingPlace.setTicket(null);
            entityManager.merge(parkingPlace);
            entityManager.remove(ticket);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
