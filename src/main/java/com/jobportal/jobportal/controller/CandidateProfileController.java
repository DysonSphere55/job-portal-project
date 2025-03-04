package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.entity.CandidateProfile;
import com.jobportal.jobportal.entity.CandidateSkills;
import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.service.CandidateProfileService;
import com.jobportal.jobportal.service.UsersService;
import com.jobportal.jobportal.util.FileUploadUtil;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

            List<CandidateSkills> skillsList = new ArrayList<>();

            if (candidateProfile.getSkills() == null) {
                skillsList.add(new CandidateSkills());
                candidateProfile.setSkills(skillsList);
            }

            model.addAttribute("profile", candidateProfile);
            model.addAttribute("skills", skillsList);

            System.out.println("candidateProfile.getId() = " + candidateProfile.getId());
            System.out.println("skillsList = " + skillsList);

        }

        return "candidate-profile";
    }

    @PostMapping("/candidate/profile/save")
    public String candidateProfileSavePage(Model model,
                                           @ModelAttribute(name = "profile") CandidateProfile candidateProfile,
                                           @RequestParam(name = "profileImage", required = false) MultipartFile photoFile,
                                           @RequestParam(name = "resume", required = false) MultipartFile resumeFile) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            Users user = usersService.findByEmail(authentication.getName())
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found with email: " + authentication.getName()));

            candidateProfile.setId(user.getId());
            candidateProfile.setUser(user);

            candidateProfile.getSkills().forEach(skill -> skill.setCandidateProfile(candidateProfile));
        }

        String photoFileName = "";
        String photoFileDir = "";

        if (!photoFile.getOriginalFilename().equals("")) {
            photoFileName = StringUtils.cleanPath(Objects.requireNonNull(photoFile.getOriginalFilename()));
            candidateProfile.setProfilePhoto(photoFileName);
        }

        photoFileDir = "import/candidate/photos/"+candidateProfile.getId();

        String resumeFileName = "";
        String resumeFileDir = "";

        if (!resumeFile.getOriginalFilename().equals("")) {
            resumeFileName = StringUtils.cleanPath(Objects.requireNonNull(resumeFile.getOriginalFilename()));
            candidateProfile.setResume(resumeFileName);
        }

        resumeFileDir = "import/candidate/photos/"+candidateProfile.getId();

        try {
            FileUploadUtil.uploadFile(photoFileName, photoFileDir, photoFile);
            FileUploadUtil.uploadFile(resumeFileName, resumeFileDir, resumeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        candidateProfileService.save(candidateProfile);

        return "redirect:/dashboard";
    }
}
