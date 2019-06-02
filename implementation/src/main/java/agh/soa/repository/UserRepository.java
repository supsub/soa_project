package agh.soa.repository;

import agh.soa.model.User;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

@Stateless
public class UserRepository extends AbstractRepository {

    public List<User> getUsers() {
        return entityManager.createQuery("SELECT u FROM User u").getResultList();
    }

    public User getUserByLogin(String username) {
        User user = null;
        List<User> users =  entityManager.createQuery("SELECT u FROM User u where u.login =:login").setParameter("login",username).getResultList();
        if (users.size()==0){
            return null;
        }
        return users.get(0);
    }

}


