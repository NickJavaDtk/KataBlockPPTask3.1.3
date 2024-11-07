package ru.kata.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kata.spring.boot_security.demo.domain.model.Role;
import ru.kata.spring.boot_security.demo.domain.model.User;
import ru.kata.spring.boot_security.demo.domain.service.UserService;
import ru.kata.spring.boot_security.demo.domain.service.UserServiceImp;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
		Role user = new Role("USER");
		Role admin = new Role("ADMIN");

		User userRoleUser = new User("user", "users", "Ваня", "Пупкин", 47);
		User adminRoleAdmin = new User("admin", "admin", "Мамкин", "Айтишник", 11);

		Set<Role> roleUserSet = new HashSet<>();
		roleUserSet.add(user);
		Set<Role> roleAdminSet = new HashSet<>();
		roleAdminSet.add(admin);
		userRoleUser.setRoleSet(roleUserSet);
		adminRoleAdmin.setRoleSet(roleAdminSet);


		UserService service = context.getBean("userServiceImp", UserServiceImp.class);
		service.addUser(userRoleUser);
		service.addUser(adminRoleAdmin);

	}

}
