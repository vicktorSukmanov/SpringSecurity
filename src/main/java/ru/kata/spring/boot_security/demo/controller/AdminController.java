package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
@Controller
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping(value = "/admin")
    public String getAllUser(Model model){
        List<User> users = userService.getListUser();
        model.addAttribute("users",users);
        return "users";
    }
    @GetMapping(value = "admin/new")
    public String getNewUser(Model model){
        model.addAttribute(new User());
        List<Role> roles = (List<Role>) roleRepository.findAll();
        model.addAttribute("roles",roles);
        return "new";

    }
    @PostMapping("/admin")
    public String createUser(@ModelAttribute("user") User user){
        userService.createUser(user);
        return "redirect:/admin";
    }
}
