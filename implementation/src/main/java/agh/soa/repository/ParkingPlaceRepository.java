package agh.soa.repository;

import agh.soa.model.ParkingPlace;
import lombok.Getter;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Stateless
@Getter
public class ParkingPlaceRepository{


    @PersistenceContext(unitName = "NewPersistenceUnit")
    private EntityManager entityManager;

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


            Query query = entityManager.createQuery("FROM ParkingPlace where taken=1 and lastTaken>:nowMinusTimeMargin order by lastTaken asc");
            query.setParameter("nowMinusTimeMargin", Date.from(new Date().toInstant().minusSeconds(marginTime)), TemporalType.TIMESTAMP);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<ParkingPlace> getParkingPlaceByOrdinalID(int ordinalID){
        try{

            Query query = entityManager.createQuery("FROM ParkingPlace where ordinalNumber=:ordinalNumber");
            query.setParameter("ordinalNumber",ordinalID);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ParkingPlace getParkingPlaceByID(int id){
        try{


            Query query = entityManager.createQuery("FROM ParkingPlace where id=:parkingPlaceID");
            query.setParameter("parkingPlaceID", id);

            return (ParkingPlace) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean changeParkingPlaceStatus(ParkingPlace parkingPlace, boolean status){
        try {

            entityManager.merge(parkingPlace);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ParkingPlace getParkingPlaceByLocation(int ordinalNumber, int parkometerID) {
        try{

            Query query = entityManager.createQuery("FROM ParkingPlace where ordinal_number=:ordinalNumber and idparkometer=:parkometerID", ParkingPlace.class);
            query.setParameter("ordinalNumber", ordinalNumber);
            query.setParameter("parkometerID", parkometerID);
            return (ParkingPlace) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
