package agh.soa.service;

import agh.soa.model.User;

import java.util.List;

public interface IUsersService {

    List<User> getUsers();

    boolean changePassword(String login, String password);
}
