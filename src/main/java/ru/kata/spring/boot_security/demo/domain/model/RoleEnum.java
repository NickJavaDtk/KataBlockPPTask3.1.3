package ru.kata.spring.boot_security.demo.domain.model;

public enum RoleEnum {
    USER, ADMIN;

    private String stringRole;

    public String getStringRole() {
        return this.toString();
    }

}
