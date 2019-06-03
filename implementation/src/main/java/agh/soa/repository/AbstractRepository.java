package agh.soa.repository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public abstract class AbstractRepository {
    protected EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
}
