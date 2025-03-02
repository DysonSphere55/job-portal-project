package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.dto.RecruiterJobPostDTO;
import com.jobportal.jobportal.entity.CandidateProfile;
import com.jobportal.jobportal.entity.RecruiterProfile;
import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.service.CandidateProfileService;
import com.jobportal.jobportal.service.JobPostService;
import com.jobportal.jobportal.service.RecruiterProfileService;
import com.jobportal.jobportal.service.UsersService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class DashboardController {

    private final UsersService usersService;
    private final RecruiterProfileService recruiterProfileService;
    private final CandidateProfileService candidateProfileService;
    private final JobPostService jobPostService;

    public DashboardController(UsersService usersService,
                               RecruiterProfileService recruiterProfileService,
                               CandidateProfileService candidateProfileService,
                               JobPostService jobPostService) {
        this.usersService = usersService;
        this.recruiterProfileService = recruiterProfileService;
        this.candidateProfileService = candidateProfileService;
        this.jobPostService = jobPostService;
    }

    @GetMapping("/dashboard")
    public String dashBoardPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersService.findByEmail(authentication.getName())
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found with email: " + authentication.getName()));

            model.addAttribute("username", user.getEmail());

            if ("RECRUITER".equals(user.getUsersType().getType())) {
                RecruiterProfile recruiterProfile = recruiterProfileService.findById(user.getId())
                        .orElseThrow(() -> new UsernameNotFoundException("Profile not found with id: " + user.getId()));

                List<RecruiterJobPostDTO> recruiterJobPosts = jobPostService.getRecruiterJobPosts(recruiterProfile.getId());

                model.addAttribute("jobPosts", recruiterJobPosts);
                model.addAttribute("profile", recruiterProfile);
            }

            if ("CANDIDATE".equals(user.getUsersType().getType())) {

                CandidateProfile candidateProfile = candidateProfileService.findById(user.getId())
                        .orElseThrow(() -> new UsernameNotFoundException("Profile not found with id: " + user.getId()));

                model.addAttribute("profile", candidateProfile);
            }
        }

        return "dashboard";
    }
}
