package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.entity.JobPost;
import com.jobportal.jobportal.entity.RecruiterProfile;
import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.service.JobPostService;
import com.jobportal.jobportal.service.RecruiterProfileService;
import com.jobportal.jobportal.service.UsersService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class JobPostController {

    private final UsersService usersService;
    private final RecruiterProfileService recruiterProfileService;
    private final JobPostService jobPostService;

    public JobPostController(UsersService usersService, RecruiterProfileService recruiterProfileService, JobPostService jobPostService) {
        this.usersService = usersService;
        this.recruiterProfileService = recruiterProfileService;
        this.jobPostService = jobPostService;
    }

    @GetMapping("/job/new")
    public String newJobPage(Model model) {

        JobPost jobPost = new JobPost();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersService.findByEmail(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            RecruiterProfile recruiterProfile = recruiterProfileService.findById(user.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Profile with id "+user.getId()+" not found"));

            jobPost.setRecruiterProfile(recruiterProfile);
        }

        model.addAttribute("jobPost", jobPost);

        return "job-post";

    }
}
