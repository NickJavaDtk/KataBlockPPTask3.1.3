package ru.kata.spring.boot_security.demo.domain.service;

import ru.kata.spring.boot_security.demo.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getRoleList();
    Optional<Role> findRoleByName(String name);
}
