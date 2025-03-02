package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.entity.RecruiterProfile;
import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.service.RecruiterProfileService;
import com.jobportal.jobportal.service.UsersService;
import com.jobportal.jobportal.util.FileUploadUtil;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RecruiterProfileControllerTest {

    @Mock
    private UsersService usersService;

    @Mock
    private RecruiterProfileService recruiterProfileService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private RecruiterProfileController recruiterProfileController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testRecruiterProfilePage_UserAuthenticated_ProfileExists() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("recruiter@test.email");

        Users mockUser = new Users();
        mockUser.setId(1);
        when(usersService.findByEmail("recruiter@test.email")).thenReturn(Optional.of(mockUser));

        RecruiterProfile mockProfile = new RecruiterProfile();
        mockProfile.setId(1);
        mockProfile.setCompany("Tech Corp");
        when(recruiterProfileService.findById(1)).thenReturn(Optional.of(mockProfile));

        String viewName = recruiterProfileController.recruiterProfilePage(model);

        verify(model, times(1)).addAttribute("profile", mockProfile);
        verify(usersService, times(1)).findByEmail("recruiter@test.email");
        verify(recruiterProfileService, times(1)).findById(1);

        assertEquals("recruiter-profile", viewName);
    }

    @Test
    void testRecruiterProfilePage_UserAuthenticated_ProfileNotFound() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");

        Users mockUser = new Users();
        mockUser.setId(1);
        when(usersService.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));

        when(recruiterProfileService.findById(1)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> recruiterProfileController.recruiterProfilePage(model));

        verify(usersService, times(1)).findByEmail("test@example.com");
        verify(recruiterProfileService, times(1)).findById(1);
    }

    @Test
    void testRecruiterProfilePage_UserNotAuthenticated() {

        AnonymousAuthenticationToken anonymousAuthentication = new AnonymousAuthenticationToken(
                "anonymous", "anonymousUser",
                Collections.singleton(new SimpleGrantedAuthority("ANONYMOUS")));

        when(securityContext.getAuthentication()).thenReturn(anonymousAuthentication);

        String viewName = recruiterProfileController.recruiterProfilePage(model);

        verifyNoInteractions(usersService);
        verifyNoInteractions(recruiterProfileService);

        assertEquals("recruiter-profile", viewName);
    }

    @Test
    void testRecruiterProfileSavePage_UserAuthenticated_ProfileExists() throws IOException {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("recruiter@email.test");

        Users mockUser = new Users();
        mockUser.setId(0);
        when(usersService.findByEmail("recruiter@email.test")).thenReturn(Optional.of(mockUser));

        when(multipartFile.getOriginalFilename()).thenReturn("profile.jpg");
        when(multipartFile.isEmpty()).thenReturn(false);

        InputStream mockInputStream = new ByteArrayInputStream("fake content".getBytes());
        when(multipartFile.getInputStream()).thenReturn(mockInputStream);

        RecruiterProfile mockProfile = new RecruiterProfile();
        when(recruiterProfileService.save(any())).thenReturn(mockProfile);

        String viewName = recruiterProfileController.recruiterProfileSavePage(model, mockProfile, multipartFile);

        verify(recruiterProfileService, times(1)).save(mockProfile);
        verify(multipartFile, times(2)).getOriginalFilename();
        verify(multipartFile, times(1)).getInputStream();

        assertEquals("redirect:/dashboard", viewName);
    }
}
