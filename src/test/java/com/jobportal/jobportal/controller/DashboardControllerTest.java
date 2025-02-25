package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.entity.CandidateProfile;
import com.jobportal.jobportal.entity.RecruiterProfile;
import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.entity.UsersType;
import com.jobportal.jobportal.service.CandidateProfileService;
import com.jobportal.jobportal.service.CustomUserDetailsService;
import com.jobportal.jobportal.service.RecruiterProfileService;
import com.jobportal.jobportal.service.UsersService;
import com.jobportal.jobportal.util.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class DashboardControllerTest {

    @Mock
    private UsersService usersService;

    @Mock
    private RecruiterProfileService recruiterProfileService;

    @Mock
    private CandidateProfileService candidateProfileService;

    @InjectMocks
    private DashboardController dashboardController;

    @Autowired
    private MockMvc mockMvc;

    private List<UsersType> usersTypes;
    private Users recruiterUser;
    private Users candidateUser;
    private RecruiterProfile recruiterProfile;
    private CandidateProfile candidateProfile;

    @BeforeEach
    void setUp() {
        dashboardController = new DashboardController(usersService, recruiterProfileService, candidateProfileService);

        UsersType recruiterType = new UsersType(1, "RECRUITER");
        UsersType candidateType = new UsersType(2, "CANDIDATE");
        usersTypes = List.of(recruiterType, candidateType);

        recruiterUser = new Users();
        recruiterUser.setId(1);
        recruiterUser.setEmail("recruiter@test.email");
        recruiterUser.setUsersType(recruiterType);

        candidateUser = new Users();
        candidateUser.setId(2);
        candidateUser.setEmail("candidate@test.email");
        candidateUser.setUsersType(candidateType);

        recruiterProfile = new RecruiterProfile();
        recruiterProfile.setId(1);
        recruiterProfile.setCompany("Big Tech ");
        recruiterProfile.setUser(recruiterUser);

        candidateProfile = new CandidateProfile();
        candidateProfile.setId(2);
        candidateProfile.setFirstName("John");
        candidateProfile.setLastName("Doe");
        candidateProfile.setUser(candidateUser);
    }

    @Test
    @WithUserDetails(value = "recruiter@test.email")
    void testDashboardPage() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attributeExists("profile"))
                .andExpect(model().attributeExists("username"))
                .andReturn();
    }

}
