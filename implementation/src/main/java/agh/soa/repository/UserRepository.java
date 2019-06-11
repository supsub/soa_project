package agh.soa.repository;

import agh.soa.model.User;

import javax.ejb.Stateless;
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
    public boolean update(String login, String password) {
        try {
            entityManager.getTransaction().begin();
            System.out.println("Searching user with login: " + login);
            User user = entityManager.find(User.class, login);
            user.setPassword(password);
            System.out.println("Succesfully changed password");
            entityManager.merge(user);
            entityManager.getTransaction().commit();
            return true;
        }catch (Exception e){
            System.err.println("No such user in database!");
        }
        return false;
    }

}


