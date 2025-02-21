package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.entity.CandidateProfile;
import com.jobportal.jobportal.entity.RecruiterProfile;
import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.service.CandidateProfileService;
import com.jobportal.jobportal.service.RecruiterProfileService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import com.jobportal.jobportal.service.UsersService;
import com.jobportal.jobportal.service.UsersTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Controller
public class UsersController {

    private final UsersService usersService;
    private final UsersTypeService usersTypeService;
    private final RecruiterProfileService recruiterProfileService;
    private final CandidateProfileService candidateProfileService;
    private final PasswordEncoder passwordEncoder;

    public UsersController(UsersService usersService,
                           UsersTypeService usersTypeService,
                           RecruiterProfileService recruiterProfileService,
                           CandidateProfileService candidateProfileService,
                           PasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.usersTypeService = usersTypeService;
        this.recruiterProfileService = recruiterProfileService;
        this.candidateProfileService = candidateProfileService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new Users());
        model.addAttribute("types", usersTypeService.getAll());
        return "register";
    }

    @PostMapping("/register/user")
    public String registerUserPage(Model model, @ModelAttribute(name = "user") Users user) {

        if (usersService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error",
                    "The email you have provided is already associated with an account.");
            model.addAttribute("user", user);
            model.addAttribute("types", usersTypeService.getAll());
            return "register";
        }

        user.setActive(true);
        user.setRegisteredDate(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users savedUser = usersService.save(user);

        String type = Objects.requireNonNull(user.getUsersType().getType());
        if (type.equals("RECRUITER")) {
            recruiterProfileService.save(new RecruiterProfile(savedUser));
        }
        if (type.equals("CANDIDATE")) {
            candidateProfileService.save(new CandidateProfile(savedUser));
        }

        return "redirect:/dashboard";
    }

}
