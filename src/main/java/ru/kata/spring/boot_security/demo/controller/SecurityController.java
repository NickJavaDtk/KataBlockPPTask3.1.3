package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.domain.service.UserService;

@Controller
public class SecurityController {
    private UserService service;

    public SecurityController(UserService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

}
