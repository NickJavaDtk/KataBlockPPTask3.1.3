package ru.kata.spring.boot_security.demo.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.domain.model.Role;
import ru.kata.spring.boot_security.demo.domain.model.User;
import ru.kata.spring.boot_security.demo.domain.service.RoleService;
import ru.kata.spring.boot_security.demo.domain.service.UserService;


import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;


    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String getStartPage(Model model, Principal principal) {
        List<User> userList = userService.getUserList();
        List<Role> roleList = roleService.getRoleList();
        String userName = principal.getName();
        User user = userService.getUserByUsername(userName).get();
        model.addAttribute("newuser", new User());
        model.addAttribute("aboutuser", user);
        model.addAttribute("role", roleList);
        model.addAttribute("list", userList);
        return "admin";
    }


    @PostMapping("/user/add")
    public String addUser(User users) {
            userService.addUser(users);
            return "redirect:/admin";
    }

    @PostMapping("/user/edit")
    public String editUser(@ModelAttribute("newuser") User users, @RequestParam("userId") String id) {
        userService.updateUser(id, users);
        return "redirect:/admin";
    }

    @GetMapping("user/delete")
    public String deleteUser(@RequestParam("userId") String id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
