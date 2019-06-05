package agh.soa.service;

import agh.soa.model.User;
import agh.soa.repository.UserRepository;
import lombok.Getter;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

@Getter
@Stateless
@Remote(IUsersService.class)
public class UsersService implements IUsersService {

    Stack stack = new Stack();

    @EJB
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public String getTestValue(){
        if(stack.empty()){
            stack.push("Julo");
            stack.push("Milosz");
            stack.push("Grzesiu");
        }
        return stack.pop().toString();
    }
}
