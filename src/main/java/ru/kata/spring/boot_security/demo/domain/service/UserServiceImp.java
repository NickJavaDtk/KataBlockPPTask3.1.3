package ru.kata.spring.boot_security.demo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.security.UserDetailsImp;
import ru.kata.spring.boot_security.demo.domain.model.User;
import ru.kata.spring.boot_security.demo.domain.repository.UserRepository;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService, UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Autowired
    BCryptPasswordEncoder encoder;

    public UserServiceImp() {
    }



    @Transactional
    @Override
    public Optional<User> getUser(String id) {
        if(checkLongValue(id)) {
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

}
