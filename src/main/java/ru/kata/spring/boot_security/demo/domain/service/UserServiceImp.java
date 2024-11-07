package ru.kata.spring.boot_security.demo.domain.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.kata.spring.boot_security.demo.domain.model.Role;
import ru.kata.spring.boot_security.demo.security.UserDetailsImp;
import ru.kata.spring.boot_security.demo.domain.model.User;
import ru.kata.spring.boot_security.demo.domain.repository.UserRepository;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private UserRepository repository;


    private BCryptPasswordEncoder encoder;

    public UserServiceImp(UserRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public Optional<User> getUser(String id) {
        if (checkLongValue(id)) {
            return repository.findById(Long.valueOf(id));
        }
        return null;
    }

    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
    }

    @Transactional
    @Override
    public void updateUser(String id, User user) {
        if (checkLongValue(id)) {
            User userTmp = getUser(id).get();
            if (userTmp != null) {
                userTmp.setPassword(encoder.encode(user.getPassword()));
                userTmp.setName(user.getName());
                userTmp.setSurname(user.getSurname());
                userTmp.setAge(user.getAge());
                userTmp.setRoleSet(user.getRoleSet());
                repository.save(userTmp);
            }
        }
    }

    @Transactional
    @Override
    public void deleteUser(String id) {
        if (checkLongValue(id)) {
            repository.deleteById(Long.parseLong(id));
        }
    }

    @Transactional
    @Override
    public List<User> getUserList() {
        return repository.findAll();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return repository.findUserByUsername(username);
    }


    private boolean checkLongValue(String longValue) {
        long idTmp = 0L;
        try {
            idTmp = Long.parseLong(longValue);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return idTmp != 0;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userTmp = getUserByUsername(username).orElse(null);
        if (userTmp == null) {
            throw new UsernameNotFoundException("Пользователь с логином " + username + " не найден");
        }
        return new UserDetailsImp(userTmp);
    }

    @Override
    public String getCheckFieldAddUser(User users, BindingResult result, Model model, RoleService roleService) {
        Optional<User> userTmp = getUserByUsername(users.getUsername());
        if (userTmp.isPresent() || users.getRoleSet().isEmpty()) {
            if (userTmp.isPresent()) {
                result.rejectValue("username", "error.username", "Данный логин уже присутствует в системе");
                List<Role> rolesList = roleService.getRoleList();
                model.addAttribute("roles", rolesList);
            }
            if (users.getRoleSet().isEmpty()) {
                result.rejectValue("roleSet", "error.roleSet", "Вы должны выбрать права пользователя");
                List<Role> rolesList = roleService.getRoleList();
                model.addAttribute("roles", rolesList);
            }
            return "admin/adduser";
        } else if (result.hasErrors()) {
            List<Role> rolesList = roleService.getRoleList();
            model.addAttribute("roles", rolesList);
            return "admin/adduser";
        }
        return null;
    }

    @Override
    public String getCheckFieldEditUser(User users, BindingResult result, Model model, RoleService roleService) {
        if (result.hasErrors()) {
            List<Role> rolesList = roleService.getRoleList();
            model.addAttribute("roles", rolesList);
            return "admin/edituser";
        }
        if (users.getRoleSet().isEmpty()) {
            result.rejectValue("roleSet", "error.roleSet", "Вы должны выбрать права пользователя");
            List<Role> rolesList = roleService.getRoleList();
            model.addAttribute("roles", rolesList);
            return "admin/edituser";
        }
        return null;
    }

}
