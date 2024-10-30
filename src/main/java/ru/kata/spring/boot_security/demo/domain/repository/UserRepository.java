package ru.kata.spring.boot_security.demo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.domain.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select distinct u from User AS u join fetch u.roleSet where u.username = :username")
    Optional<User> findUserByUsername(String username);
}
