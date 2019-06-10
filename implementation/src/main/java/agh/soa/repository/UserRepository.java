package agh.soa.repository;

import agh.soa.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UserRepository {

    @PersistenceContext(unitName = "NewPersistenceUnit")
    private EntityManager entityManager;

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


