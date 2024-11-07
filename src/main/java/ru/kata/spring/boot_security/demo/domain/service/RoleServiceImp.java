package ru.kata.spring.boot_security.demo.domain.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.domain.model.Role;
import ru.kata.spring.boot_security.demo.domain.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {
    private RoleRepository repository;

    public RoleServiceImp(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<Role> getRoleList() {
        return (List<Role>) repository.findAll();
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        return repository.findRoleByName(name);
    }
}
