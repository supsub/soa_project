package agh.soa.service;

import agh.soa.model.User;

import java.util.List;

public interface IUsersService {

    List<User> getUsers();

    User getUserByLogin(String login);
}
