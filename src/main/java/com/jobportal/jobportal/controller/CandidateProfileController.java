package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.entity.CandidateProfile;
import com.jobportal.jobportal.entity.CandidateSkills;
import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.service.CandidateProfileService;
import com.jobportal.jobportal.service.UsersService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Controller
public class CandidateProfileController {

    private final UsersService usersService;
    private final CandidateProfileService candidateProfileService;

    public CandidateProfileController(UsersService usersService, CandidateProfileService candidateProfileService) {
        this.usersService = usersService;
        this.candidateProfileService = candidateProfileService;
    }

    @GetMapping("/candidate/profile")
    public String candidateProfilePage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            Users user = usersService.findByEmail(authentication.getName())
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found with email: " + authentication.getName()));

            CandidateProfile candidateProfile = candidateProfileService.findById(user.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Profile not found with id: " + user.getId()));

            List<CandidateSkills> emptySkillsList = new ArrayList<>();
            emptySkillsList.add(new CandidateSkills());

            if (candidateProfile.getSkills() == null) {
                candidateProfile.setSkills(emptySkillsList);
            }

            model.addAttribute("profile", candidateProfile);
            model.addAttribute("skills", emptySkillsList);

        }

        return "candidate-profile";
    }

    @PostMapping("/candidate/profile/save")
    public String candidateProfileSavePage(Model model,
                                           @ModelAttribute(name = "profile") CandidateProfile candidateProfile,
                                           @RequestParam(name = "profileImage") MultipartFile photoFile,
                                           @RequestParam(name = "resumeFile") MultipartFile resumeFile) {


        return "redirect:/dashboard";
    }
}
