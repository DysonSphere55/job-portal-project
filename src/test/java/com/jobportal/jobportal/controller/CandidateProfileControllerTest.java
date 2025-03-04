package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.entity.CandidateProfile;
import com.jobportal.jobportal.entity.CandidateSkills;
import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.service.CandidateProfileService;
import com.jobportal.jobportal.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CandidateProfileControllerTest {

    @Mock
    private UsersService usersService;

    @Mock
    private CandidateProfileService candidateProfileService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private MultipartFile testProfilePhotoFile;

    @Mock
    private MultipartFile testResumeFile;

    @InjectMocks
    private CandidateProfileController candidateProfileController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testCandidateProfilePage_UserAuthenticated_ProfileExists() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("candidate@test.email");

        Users testUser = new Users();
        testUser.setId(1);
        when(usersService.findByEmail("candidate@test.email")).thenReturn(Optional.of(testUser));

        CandidateProfile testProfile = new CandidateProfile();
        testProfile.setId(1);
        testProfile.setFirstName("John");
        testProfile.setLastName("Doe");
        when(candidateProfileService.findById(1)).thenReturn(Optional.of(testProfile));

        String viewName = candidateProfileController.candidateProfilePage(model);

        verify(model, times(1)).addAttribute("profile", testProfile);
        verify(usersService, times(1)).findByEmail("candidate@test.email");
        verify(candidateProfileService, times(1)).findById(1);

        assertEquals("candidate-profile", viewName);
    }

    @Test
    void testRecruiterProfilePage_UserAuthenticated_ProfileNotFound() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");

        Users mockUser = new Users();
        mockUser.setId(1);
        when(usersService.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));

        when(candidateProfileService.findById(1)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> candidateProfileController.candidateProfilePage(model));

        verify(usersService, times(1)).findByEmail("test@example.com");
        verify(candidateProfileService, times(1)).findById(1);
    }

    @Test
    void testRecruiterProfilePage_UserNotAuthenticated() {

        AnonymousAuthenticationToken anonymousAuthentication = new AnonymousAuthenticationToken(
                "anonymous", "anonymousUser",
                Collections.singleton(new SimpleGrantedAuthority("ANONYMOUS")));

        when(securityContext.getAuthentication()).thenReturn(anonymousAuthentication);

        String viewName = candidateProfileController.candidateProfilePage(model);

        verifyNoInteractions(usersService);
        verifyNoInteractions(candidateProfileService);

        assertEquals("candidate-profile", viewName);
    }

    @Test
    void testRecruiterProfileSavePage_UserAuthenticated_ProfileExists() throws IOException {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("candidate@email.test");

        Users testUser = new Users();
        testUser.setId(0);
        when(usersService.findByEmail("candidate@email.test")).thenReturn(Optional.of(testUser));

        when(testProfilePhotoFile.getOriginalFilename()).thenReturn("profile.jpg");
        when(testProfilePhotoFile.isEmpty()).thenReturn(false);
        when(testResumeFile.getOriginalFilename()).thenReturn("resume.pdf");
        when(testResumeFile.isEmpty()).thenReturn(false);

        InputStream mockInputStream = new ByteArrayInputStream("fake content".getBytes());
        when(testProfilePhotoFile.getInputStream()).thenReturn(mockInputStream);
        when(testResumeFile.getInputStream()).thenReturn(mockInputStream);

        CandidateProfile testProfile = new CandidateProfile();
        when(candidateProfileService.save(any())).thenReturn(testProfile);
        List<CandidateSkills> skillsList = new ArrayList<>();
        skillsList.add(new CandidateSkills());
        testProfile.setSkills(skillsList);

        String viewName = candidateProfileController
                .candidateProfileSavePage(model, testProfile, testProfilePhotoFile, testResumeFile);

        verify(candidateProfileService, times(1)).save(testProfile);
        verify(testProfilePhotoFile, times(2)).getOriginalFilename();
        verify(testProfilePhotoFile, times(1)).getInputStream();
        verify(testResumeFile, times(2)).getOriginalFilename();
        verify(testResumeFile, times(1)).getInputStream();

        assertEquals("redirect:/dashboard", viewName);
    }
}
