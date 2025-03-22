package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.dto.RecruiterJobPostDTO;
import com.jobportal.jobportal.entity.CandidateProfile;
import com.jobportal.jobportal.entity.JobPost;
import com.jobportal.jobportal.entity.RecruiterProfile;
import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.service.CandidateProfileService;
import com.jobportal.jobportal.service.JobPostService;
import com.jobportal.jobportal.service.RecruiterProfileService;
import com.jobportal.jobportal.service.UsersService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    @GetMapping("/dashboard/**")
    public String dashBoardPage(Model model,
                                @RequestParam(name = "job", required = false) String job,
                                @RequestParam(name = "location", required = false) String location,
                                @RequestParam(name = "partTime", required = false) String partTime,
                                @RequestParam(name = "fullTime", required = false) String fullTime,
                                @RequestParam(name = "freelance", required = false) String freelance,
                                @RequestParam(name = "remoteOnly", required = false) String remoteOnly,
                                @RequestParam(name = "officeOnly", required = false) String officeOnly,
                                @RequestParam(name = "partialRemote", required = false) String partialRemote,
                                @RequestParam(name = "today", required = false) boolean today,
                                @RequestParam(name = "days7", required = false) boolean days7,
                                @RequestParam(name = "days30", required = false) boolean days30) {

        model.addAttribute("job", job);
        model.addAttribute("location", location);
        model.addAttribute("partTime", Objects.equals(partTime, "Part-Time"));
        model.addAttribute("fullTime", Objects.equals(fullTime, "Full-Time"));
        model.addAttribute("freelance", Objects.equals(freelance, "Freelance"));
        model.addAttribute("remoteOnly", Objects.equals(remoteOnly, "Remote-Only"));
        model.addAttribute("officeOnly", Objects.equals(officeOnly, "Office-Only"));
        model.addAttribute("partialRemote", Objects.equals(partialRemote, "Partial-Remote"));
        model.addAttribute("today", today);
        model.addAttribute("days7", days7);
        model.addAttribute("days30", days30);


        boolean isTypeFilter = true;
        if (partTime == null && fullTime == null && freelance == null) {
            partTime = "Part-Time";
            fullTime = "Full-Time";
            freelance = "Freelance";
            isTypeFilter = false;
        }

        boolean isRemoteFilter = true;
        if (remoteOnly == null && officeOnly == null && partialRemote == null   ) {
            remoteOnly = "Remote-Only";
            officeOnly = "Office-Only";
            partialRemote = "Partial-Remote";
            isRemoteFilter = false;
        }

        boolean isDateFilter = false;
        LocalDate searchDate = null;
        if (today) {
            isDateFilter = true;
            searchDate = LocalDate.now();
        } else if (days7) {
            isDateFilter = true;
            searchDate = LocalDate.now().minusDays(7);
        } else if (days30) {
            isDateFilter = true;
            searchDate = LocalDate.now().minusDays(30);
        }


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

                List<JobPost> candidateJobPosts = new ArrayList<>();

                if (!StringUtils.hasText(job) && !StringUtils.hasText(location) &&
                        !isTypeFilter && !isRemoteFilter && !isDateFilter) {
                    candidateJobPosts = jobPostService.getAll();
                } else {
                    candidateJobPosts = jobPostService.getWithFilters(
                            job, location,
                            Arrays.asList(partTime, fullTime, freelance),
                            Arrays.asList(remoteOnly, officeOnly, partialRemote),
                            searchDate);
                }

                model.addAttribute("profile", candidateProfile);
                model.addAttribute("jobPosts", candidateJobPosts);
            }
        }

        return "dashboard";
    }
}
