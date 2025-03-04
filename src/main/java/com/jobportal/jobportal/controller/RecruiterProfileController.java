package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.entity.RecruiterProfile;
import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.service.RecruiterProfileService;
import com.jobportal.jobportal.service.UsersService;
import com.jobportal.jobportal.util.FileUploadUtil;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
public class RecruiterProfileController {

    private final UsersService usersService;
    private final RecruiterProfileService recruiterProfileService;

    public RecruiterProfileController(UsersService usersService, RecruiterProfileService recruiterProfileService) {
        this.usersService = usersService;
        this.recruiterProfileService = recruiterProfileService;
    }

    @GetMapping("/recruiter/profile")
    public String recruiterProfilePage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            Users user = usersService.findByEmail(authentication.getName())
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found with email: " + authentication.getName()));

            RecruiterProfile recruiterProfile = recruiterProfileService.findById(user.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Profile not found with id: " + user.getId()));

            model.addAttribute("profile", recruiterProfile);

        }

        return "recruiter-profile";
    }

    @PostMapping("/recruiter/profile/save")
    public String recruiterProfileSavePage(Model model,
                                           @ModelAttribute(name = "profile") RecruiterProfile recruiterProfile,
                                           @RequestParam(name = "profileImage", required = false) MultipartFile photoFile) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            Users user = usersService.findByEmail(authentication.getName())
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found with email: " + authentication.getName()));

            recruiterProfile.setId(user.getId());
            recruiterProfile.setUser(user);
        }

        String fileName = "";
        String dirName = "";

        if (!photoFile.getOriginalFilename().equals("")) {
            fileName = StringUtils.cleanPath(Objects.requireNonNull(photoFile.getOriginalFilename()));
            recruiterProfile.setProfilePhoto(fileName);
        }

        RecruiterProfile savedProfile = recruiterProfileService.save(recruiterProfile);

        dirName = "import/recruiter/photos/" + savedProfile.getId();

        try {
            FileUploadUtil.uploadFile(fileName, dirName, photoFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/dashboard";
    }
}
