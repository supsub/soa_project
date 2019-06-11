package agh.soa.repository;

import agh.soa.model.User;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

@ApplicationScoped
@ManagedBean
public class UserRepository {

    private EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

    public List<User> getUsers() {
        return entityManager.createQuery("SELECT u FROM User u").getResultList();
    }

    public User getUserByLogin(String username) {
        User user = null;
        List<User> users = entityManager.createQuery("SELECT u FROM User u where u.login =:login").setParameter("login", username).getResultList();
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

}


