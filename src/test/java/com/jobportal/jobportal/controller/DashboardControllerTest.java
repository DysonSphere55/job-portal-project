package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.entity.CandidateProfile;
import com.jobportal.jobportal.entity.RecruiterProfile;
import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.entity.UsersType;
import com.jobportal.jobportal.service.CandidateProfileService;
import com.jobportal.jobportal.service.RecruiterProfileService;
import com.jobportal.jobportal.service.UsersService;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DashboardControllerTest {
    @Mock
    private UsersService usersService;
    @Mock
    private RecruiterProfileService recruiterProfileService;
    @Mock
    private CandidateProfileService candidateProfileService;
    @Mock
    private Model model;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;
    @InjectMocks
    private DashboardController dashboardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testDashboardPage_RecruiterUserAuthenticated() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("recruiter@test.email");

        UsersType testRecruiterType = new UsersType();
        testRecruiterType.setId(1);
        testRecruiterType.setType("RECRUITER");

        Users testUser = new Users();
        testUser.setId(1);
        testUser.setEmail("recruiter@test.email");
        testUser.setUsersType(testRecruiterType);
        when(usersService.findByEmail("recruiter@test.email")).thenReturn(Optional.of(testUser));

        RecruiterProfile testRecruiterProfile = new RecruiterProfile();
        testRecruiterProfile.setId(1);
        testRecruiterProfile.setCompany("Tech Corp");
        when(recruiterProfileService.findById(testUser.getId())).thenReturn(Optional.of(testRecruiterProfile));

        String viewName = dashboardController.dashBoardPage(model);

        verify(usersService, times(1)).findByEmail("recruiter@test.email");
        verify(recruiterProfileService, times(1)).findById(testUser.getId());
        verify(model).addAttribute("profile", testRecruiterProfile);

        assertEquals("dashboard", viewName);
    }

    @Test
    void testDashboardPage_CandidateUserAuthenticated() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("candidate@test.email");

        UsersType testCandidateType = new UsersType();
        testCandidateType.setId(2);
        testCandidateType.setType("CANDIDATE");

        Users testUser = new Users();
        testUser.setId(2);
        testUser.setEmail("candidate@test.email");
        testUser.setUsersType(testCandidateType);
        when(usersService.findByEmail("candidate@test.email")).thenReturn(Optional.of(testUser));

        CandidateProfile testCandidateProfile = new CandidateProfile();
        testCandidateProfile.setId(2);
        testCandidateProfile.setFirstName("John");
        testCandidateProfile.setLastName("Doe");
        when(candidateProfileService.findById(testUser.getId())).thenReturn(Optional.of(testCandidateProfile));

        String viewName = dashboardController.dashBoardPage(model);

        verify(usersService, times(1)).findByEmail("candidate@test.email");
        verify(candidateProfileService, times(1)).findById(testUser.getId());
        verify(model).addAttribute("profile", testCandidateProfile);

        assertEquals("dashboard", viewName);
    }

    @Test
    void testDashboardPage_UserNotAuthenticated() {
        AnonymousAuthenticationToken anonymousAuthentication = new AnonymousAuthenticationToken(
                "anonymous", "anonymous", List.of(new SimpleGrantedAuthority("ANONYMOUS_USER"))
        );

        when(securityContext.getAuthentication()).thenReturn(anonymousAuthentication);

        String viewName = dashboardController.dashBoardPage(model);

        verifyNoInteractions(usersService);
        verifyNoInteractions(recruiterProfileService);
        verifyNoInteractions(candidateProfileService);

        assertEquals("dashboard", viewName);
    }

}
