package ru.kata.spring.boot_security.demo.domain.service;



import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    public String getCheckFieldAddUser(User users, BindingResult result, Model model, RoleService roleService);

    public String getCheckFieldEditUser(User users, BindingResult result, Model model, RoleService roleService);


}
