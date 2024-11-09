package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.domain.model.User;
import ru.kata.spring.boot_security.demo.domain.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping()
    public String getUserPage(Principal principal, Model model) {
        final String name = principal.getName();
        User user = service.getUserByUsername(name).get();
        model.addAttribute("aboutuser", user);
        return "user";
    }
}
