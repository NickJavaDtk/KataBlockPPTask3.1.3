package ru.kata.spring.boot_security.demo.domain.service;



import ru.kata.spring.boot_security.demo.domain.model.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
    Optional<User> getUser(String id);

    void addUser(User user);

    void updateUser(String id, User user);

    void deleteUser(String id);

    List<User> getUserList();

    Optional<User> getUserByUsername(String username);


}
