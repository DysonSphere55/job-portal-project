package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.entity.*;
import com.jobportal.jobportal.service.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class JobPostController {

    private final UsersService usersService;
    private final RecruiterProfileService recruiterProfileService;
    private final JobPostService jobPostService;
    private final CandidateJobApplyService candidateJobApplyService;
    private final CandidateJobSaveService candidateJobSaveService;
    private final CandidateProfileService candidateProfileService;

    public JobPostController(UsersService usersService, RecruiterProfileService recruiterProfileService,
                             JobPostService jobPostService, CandidateJobApplyService candidateJobApplyService,
                             CandidateJobSaveService candidateJobSaveService,
                             CandidateProfileService candidateProfileService) {
        this.usersService = usersService;
        this.recruiterProfileService = recruiterProfileService;
        this.jobPostService = jobPostService;
        this.candidateJobApplyService = candidateJobApplyService;
        this.candidateJobSaveService = candidateJobSaveService;
        this.candidateProfileService = candidateProfileService;
    }

    @GetMapping("/job/new")
    public String jobPostPage(Model model) {

        JobPost jobPost = new JobPost();

        model.addAttribute("jobPost", jobPost);

        return "job-post";

    }

    @PostMapping("/job/new/save")
    public String jobPostSavePage(@ModelAttribute(name = "jobPost") JobPost jobPost, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersService.findByEmail(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            RecruiterProfile recruiterProfile = recruiterProfileService.findById(user.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Profile with id "+user.getId()+" not found"));

            jobPost.setRecruiterProfile(recruiterProfile);

            jobPost.setPostedDate(LocalDateTime.now());

            jobPostService.save(jobPost);
        }

        return "redirect:/dashboard";
    }

    @GetMapping("/job/detail/{id}")
    public String jobDetails(@PathVariable("id") int jobPostId, Model model) {

        JobPost jobPost = jobPostService.findById(jobPostId)
                .orElseThrow(() -> new EntityNotFoundException("Job not found with id -" + jobPostId));
        LocalDateTime postedDate = jobPost.getPostedDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("RECRUITER"))) {
                List<CandidateJobApply> appliedJobs = candidateJobApplyService.findByJobPost(jobPost);
                List<CandidateProfile> appliedCandidates = new ArrayList<>();
                for (CandidateJobApply appliedJob : appliedJobs) {
                    appliedCandidates.add(appliedJob.getCandidateProfile());
                }
                model.addAttribute("appliedCandidates", appliedCandidates);
            }

            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("CANDIDATE"))) {
                Users user = usersService.findByEmail(authentication.getName()).orElseThrow(
                        () -> new UsernameNotFoundException("User not found with email: "+ authentication.getName())
                );
                CandidateProfile profile = candidateProfileService.findById(user.getId()).orElseThrow(
                        () -> new UsernameNotFoundException("Profile not found with id: "+user.getId())
                );
                List<CandidateJobApply> appliedJobs = candidateJobApplyService.findByCandidateProfile(profile);
                List<CandidateJobSave> savedJobs = candidateJobSaveService.findByCandidateProfile(profile);
                for (CandidateJobApply appliedJob : appliedJobs) {
                    if (Objects.equals(
                            appliedJob.getJobPost().getId(),
                            jobPost.getId()
                    )) {
                        jobPost.setApplied(true);
                        break;
                    }
                }
                for (CandidateJobSave savedJob : savedJobs) {
                    if (Objects.equals(
                            savedJob.getJobPost().getId(),
                            jobPost.getId()
                    )) {
                        jobPost.setSaved(true);
                        break;
                    }
                }
            }
        }

        model.addAttribute("job", jobPost);
        model.addAttribute("jobPostId", jobPostId);
        model.addAttribute("postedDate", postedDate);

        return "job-detail";
    }

    @GetMapping("/job/detail/delete/{id}")
    public String deleteJob(@PathVariable("id") int jobPostId, Model model) {

        jobPostService.deleteById(jobPostId);

        return "redirect:/dashboard/**";
    }

    @GetMapping("/job/detail/edit/{id}")
    public String editJob(@PathVariable("id") int jobPostId, Model model) {

        JobPost jobPost = jobPostService.findById(jobPostId).orElseThrow(
                () -> new EntityNotFoundException("Job not found with id: "+ jobPostId)
        );

        model.addAttribute("job", jobPost);

        return "add-job";
    }

    @GetMapping("/job/apply/{id}")
    public String applyJob(@PathVariable("id") int jobPostId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof SecurityContextHolder)) {
            Users user = usersService.findByEmail(authentication.getName()).orElseThrow(
                    () -> new UsernameNotFoundException("User not found -"+authentication.getName()));

            CandidateProfile profile = candidateProfileService.findById(user.getId()).orElseThrow(
                    () -> new UsernameNotFoundException("Profile not found -"+user.getId()));

            JobPost job = jobPostService.findById(jobPostId).orElseThrow(
                    () -> new EntityNotFoundException("Job not found -"+jobPostId));

            CandidateJobApply applyJob = new CandidateJobApply();
            applyJob.setCandidateProfile(profile);
            applyJob.setJobPost(job);
            applyJob.setApplyDate(LocalDateTime.now());

            candidateJobApplyService.save(applyJob);

        }

        return "redirect:/dashboard";
    }

    @GetMapping("/job/save/{id}")
    public String saveJob(@PathVariable("id") int jobPostId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof SecurityContextHolder)) {
            Users user = usersService.findByEmail(authentication.getName()).orElseThrow(
                    () -> new UsernameNotFoundException("User not found -"+authentication.getName()));

            CandidateProfile profile = candidateProfileService.findById(user.getId()).orElseThrow(
                    () -> new UsernameNotFoundException("Profile not found -"+user.getId()));

            JobPost job = jobPostService.findById(jobPostId).orElseThrow(
                    () -> new EntityNotFoundException("Job not found -"+jobPostId));

            CandidateJobSave saveJob = new CandidateJobSave();
            saveJob.setCandidateProfile(profile);
            saveJob.setJobPost(job);

            candidateJobSaveService.save(saveJob);

        }

        return "redirect:/dashboard";
    }
}
