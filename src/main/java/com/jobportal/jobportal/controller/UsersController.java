package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.entity.Users;
import org.springframework.ui.Model;
import com.jobportal.jobportal.service.UsersService;
import com.jobportal.jobportal.service.UsersTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    private final UsersService usersService;
    private final UsersTypeService usersTypeService;

    public UsersController(UsersService usersService, UsersTypeService usersTypeService) {
        this.usersService = usersService;
        this.usersTypeService = usersTypeService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new Users());
        model.addAttribute("types", usersTypeService.getAll());
        return "register";
    }

    @PostMapping("/register/user")
    public String registerUserPage(Model model, Users user) {

        if (usersService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error",
                    "The email you have provided is already associated with an account.");
            model.addAttribute("user", user);
            model.addAttribute("types", usersTypeService.getAll());
            return "register";
        }

        usersService.save(user);

        return "dashboard";

    }
}
