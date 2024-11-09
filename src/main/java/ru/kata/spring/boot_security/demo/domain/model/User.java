package ru.kata.spring.boot_security.demo.domain.model;




import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;
//    @NotBlank(message = "Логин не должен быть пустым")
//    @Column(name = "login")
    private String username;
//    @NotBlank(message = "Пароль не должно быть пустым")
//    @Size(min = 5, message = "Минимальная длина пароль 5 символов")
    private String password;
//    @NotBlank(message = "Имя не должно быть пустым")
//    @Size(min = 2, message = "Имя должно включать минимум два символа")
    private String name;
//    @NotBlank(message = "Фамилия не должна быть пустой")
//    @Size(min = 2, message = "Фамилия должна включать минимум два символа")
    private String surname;
//    @Min(value = 0, message = "возраст не может быть меньше 0")
    private Integer age;


    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "users_id"),
        inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roleSet = new HashSet<>();

    public User() {
    }

    public User(String username, String password, String name, String surname, Integer age) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age != null ? age : 1;
    }

    public void setAge(Integer age) {
           this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    @Override
    public String toString() {
        return "Пользователь{" +
                "id=" + id +
                ", Логин='" + username + '\'' +
                ", Пароль='" + password + '\'' +
                ", Имя='" + name + '\'' +
                ", Фамилия='" + surname + '\'' +
                ", Возраст=" + age +
                ", Список ролей=" + roleSet +
                '}';
    }
}
