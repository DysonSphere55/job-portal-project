package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.entity.CandidateProfile;
import com.jobportal.jobportal.entity.RecruiterProfile;
import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.service.CandidateProfileService;
import com.jobportal.jobportal.service.RecruiterProfileService;
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
    private final RecruiterProfileService recruiterProfileService;
    private final CandidateProfileService candidateProfileService;

    public UsersController(UsersService usersService,
                           UsersTypeService usersTypeService,
                           RecruiterProfileService recruiterProfileService,
                           CandidateProfileService candidateProfileService) {
        this.usersService = usersService;
        this.usersTypeService = usersTypeService;
        this.recruiterProfileService = recruiterProfileService;
        this.candidateProfileService = candidateProfileService;
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

        Users savedUser = usersService.save(user);

        if (user.getUsersType().getType().equals("RECRUITER")) {
            recruiterProfileService.save(new RecruiterProfile(savedUser));
        }

        if (user.getUsersType().getType().equals("CANDIDATE")) {
            candidateProfileService.save(new CandidateProfile(savedUser));
        }

        return "dashboard";

    }
}
