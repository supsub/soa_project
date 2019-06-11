package agh.soa.repository;

import agh.soa.dto.TicketDTO;
import agh.soa.exceptions.NoSuchParkingPlaceException;
import agh.soa.exceptions.NoSuchUserException;
import agh.soa.exceptions.PlaceAlreadyTakenException;
import agh.soa.model.ParkingPlace;
import agh.soa.model.Ticket;
import agh.soa.model.User;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@ManagedBean
public class TicketRepository{


    private EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

    @Inject
    UserRepository userRepository;

    @Inject
    ParkingPlaceRepository parkingPlaceRepository;


    public List<Ticket> getAllActiveTickets() {
        List<Ticket> result = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("FROM Ticket where expirationTime>:now order by expirationTime asc");
            query.setParameter("now", new Date(), TemporalType.TIMESTAMP);
            result = query.getResultList();
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Ticket> getAllActiveTicketsForParkingPlace(int parkingPlaceId) {
        List<Ticket> result = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("FROM Ticket where expirationTime>:now and parkingPlace.id=:parkingPlaceId order by expirationTime asc");
            query.setParameter("parkingPlaceId", parkingPlaceId);
            query.setParameter("now", new Date(), TemporalType.TIMESTAMP);
            result = query.getResultList();
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Ticket getLastTicketForParkingPlace(int parkingPlaceId) {
        Ticket result;
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("FROM Ticket where parkingPlace.id=:parkingPlaceId order by expirationTime desc", Ticket.class);
            query.setMaxResults(1);
            query.setParameter("parkingPlaceId", parkingPlaceId);
            result = (Ticket) query.getSingleResult();
            return result;
        }catch (NoResultException nre){
            //System.out.println("no result for: parking place id:  " + parkingPlaceId);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            entityManager.getTransaction().commit();
        }
        return null;
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

            List<Ticket> parkingPlaceTickets = getAllActiveTicketsForParkingPlace(parkingPlace.getId());
            if (parkingPlaceTickets.size() != 0) {
                System.out.println("Parking place has ticket already");
                throw new PlaceAlreadyTakenException();
            }

            User user = userRepository.getUserByLogin(ticketDTO.getOwner());


            if (user == null) {
                System.out.println("No such user: " + ticketDTO.getOwner());
                throw new NoSuchUserException();
            }


            Ticket ticket = new Ticket(expirationDate, user, parkingPlace);
            parkingPlace.add(ticket);
            entityManager.persist(ticket);


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




}


