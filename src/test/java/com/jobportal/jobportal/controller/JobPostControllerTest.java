package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.entity.JobPost;
import com.jobportal.jobportal.entity.RecruiterProfile;
import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.service.JobPostService;
import com.jobportal.jobportal.service.RecruiterProfileService;
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
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JobPostControllerTest {

    @Mock
    private JobPostService jobPostService;
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
    @InjectMocks
    private JobPostController jobPostController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testJobPostPage() {
        String viewName = jobPostController.jobPostPage(model);

        verify(model).addAttribute(eq("jobPost"), any(JobPost.class));

        assertEquals("job-post", viewName);
    }

    @Test
    void testJobPostSavePage_Success() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("recruiter@email.test");

        Users testUser = new Users();
        testUser.setId(0);
        when(usersService.findByEmail("recruiter@email.test")).thenReturn(Optional.of(testUser));

        RecruiterProfile testProfile = new RecruiterProfile();
        testProfile.setId(0);
        testProfile.setUser(testUser);
        when(recruiterProfileService.findById(0)).thenReturn(Optional.of(testProfile));

        JobPost testJob = new JobPost();
        testJob.setTitle("Test Job Title");
        when(jobPostService.save(testJob)).thenReturn(testJob);

        String viewName = jobPostController.jobPostSavePage(testJob, model);

        verify(usersService, times(1)).findByEmail("recruiter@email.test");
        verify(recruiterProfileService, times(1)).findById(0);
        verify(jobPostService, times(1)).save(testJob);

        assertEquals("redirect:/dashboard", viewName);
        assertEquals(0, testJob.getRecruiterProfile().getId());
    }

    @Test
    void testJobPostSavePage_UserNotAuthorized() {
        AnonymousAuthenticationToken anonymousAuthentication = new AnonymousAuthenticationToken(
                "anonymous", "anonymousUser",
                Collections.singleton(new SimpleGrantedAuthority("ANONYMOUS")));

        when(securityContext.getAuthentication()).thenReturn(anonymousAuthentication);

        JobPost testJob = new JobPost();
        testJob.setTitle("Test Job Title");

        String viewName = jobPostController.jobPostSavePage(testJob, model);

        verifyNoInteractions(usersService);
        verifyNoInteractions(recruiterProfileService);
        verifyNoInteractions(jobPostService);

        assertEquals("redirect:/dashboard", viewName);
    }
}
