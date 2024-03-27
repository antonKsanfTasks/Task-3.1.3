package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/users")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {

        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.findAll();
        Set<Role> roles = roleService.findAll();
        List<Role> allRoles = new ArrayList<>(roles);

        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", allRoles);

        return "admin/users/list";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/users/";
    }


    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        Set<Role> roles = roleService.findAll();
        List<Role> allRoles = new ArrayList<>(roles);

        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        return "admin/users/edit";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin/users/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users/";
    }

}
