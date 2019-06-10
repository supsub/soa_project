package agh.soa.repository;

import agh.soa.dto.TicketDTO;
import agh.soa.exceptions.NoSuchParkingPlaceException;
import agh.soa.model.ParkingPlace;
import lombok.Getter;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@ManagedBean
@Getter
public class ParkingPlaceRepository{


    private EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

    private final int marginTime = 10;

    public List<ParkingPlace> getAllParkingPlaces(){
        try{
            Query query = entityManager.createQuery("FROM ParkingPlace");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void update(ParkingPlace pp){
        try{

            entityManager.merge(pp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ParkingPlace> getOrderedTakenParkingPlaces(){

        try{

            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("FROM ParkingPlace where taken=1 and lastTaken>:nowMinusTimeMargin order by lastTaken asc");
            query.setParameter("nowMinusTimeMargin", Date.from(new Date().toInstant().minusSeconds(marginTime)), TemporalType.TIMESTAMP);
            entityManager.getTransaction().commit();
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<ParkingPlace> getParkingPlaceByOrdinalID(int ordinalID){
        try{
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("FROM ParkingPlace where ordinalNumber=:ordinalNumber");
            query.setParameter("ordinalNumber",ordinalID);
            entityManager.getTransaction().commit();
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ParkingPlace getParkingPlaceByID(int id){
        try{

            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("FROM ParkingPlace where id=:parkingPlaceID");
            query.setParameter("parkingPlaceID", id);
            entityManager.getTransaction().commit();
            return (ParkingPlace) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean changeParkingPlaceStatus(ParkingPlace parkingPlace, boolean status){
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(parkingPlace);
            entityManager.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ParkingPlace getParkingPlaceByLocation(int ordinalNumber, int parkometerID) {
        try{
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("FROM ParkingPlace where ordinal_number=:ordinalNumber and idparkometer=:parkometerID", ParkingPlace.class);
            query.setParameter("ordinalNumber", ordinalNumber);
            query.setParameter("parkometerID", parkometerID);
            entityManager.getTransaction().commit();
            return (ParkingPlace) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ParkingPlace getParkingPlaceFromTicketDTO(TicketDTO ticketDTO) {

        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("FROM ParkingPlace where ordinalNumber=:ordinalNumber and " +
                    "parkometer.ordinalNumber=:parkometer and " +
                    "parkometer.street.name=:street");
            query.setParameter("ordinalNumber", ticketDTO.getOrdinalNumber());
            query.setParameter("parkometer", ticketDTO.getParkometerOrd());
            query.setParameter("street", ticketDTO.getStreet());
            List parkingPlaces = query.getResultList();
            return (ParkingPlace) parkingPlaces.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
