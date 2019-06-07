package agh.soa.repository;

import agh.soa.model.ParkingPlace;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ParkingPlaceRepository extends AbstractRepository {

    public List<ParkingPlace> getAllParkingPlaces(){
        try{
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("FROM ParkingPlace");
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
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ParkingPlace getParkingPlaceByID(int id){
        try{
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("FROM ParkingPlace where idparking_place=:parkingPlaceID", ParkingPlace.class);
            query.setParameter("parkingPlaceID", id);
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
            return (ParkingPlace) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
