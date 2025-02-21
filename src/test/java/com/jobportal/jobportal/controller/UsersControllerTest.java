package com.jobportal.jobportal.controller;


import com.jobportal.jobportal.entity.CandidateProfile;
import com.jobportal.jobportal.entity.RecruiterProfile;
import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.entity.UsersType;
import com.jobportal.jobportal.service.CandidateProfileService;
import com.jobportal.jobportal.service.RecruiterProfileService;
import com.jobportal.jobportal.service.UsersService;
import com.jobportal.jobportal.service.UsersTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersControllerTest {

    @Mock
    private UsersService usersService;

    @Mock
    private UsersTypeService usersTypeService;

    @Mock
    private RecruiterProfileService recruiterProfileService;

    @Mock
    private CandidateProfileService candidateProfileService;

    @Mock
    private Model model;

    @InjectMocks
    private UsersController usersController;

    private List<UsersType> usersTypes;
    private Users recruiterNewUser;
    private Users candidateNewUser;

    @BeforeEach
    void setUp() {
        UsersType recruiterType = new UsersType(1, "RECRUITER");
        UsersType candidateType = new UsersType(2, "CANDIDATE");
        usersTypes = List.of(recruiterType, candidateType);

        recruiterNewUser = new Users();
        recruiterNewUser.setId(1);
        recruiterNewUser.setEmail("recruiter@email.com");
        recruiterNewUser.setUsersType(recruiterType);

        candidateNewUser = new Users();
        candidateNewUser.setId(2);
        candidateNewUser.setEmail("candidate@email.com");
        candidateNewUser.setUsersType(candidateType);
    }

    @Test
    void testRegisterPage() {
        when(usersTypeService.getAll()).thenReturn(usersTypes);

        String viewName = usersController.registerPage(model);

        verify(model).addAttribute(eq("user"), any(Users.class));
        verify(model).addAttribute("types", usersTypes);

        assertEquals("register", viewName);
    }

    @Test
    void testRegisterUserPageRecruiterSuccess() {
        when(usersService.findByEmail(recruiterNewUser.getEmail())).thenReturn(Optional.empty());

        String viewName = usersController.registerUserPage(model, recruiterNewUser);

        verify(usersService, times(1)).save(recruiterNewUser);
        verify(recruiterProfileService, times(1)).save(any(RecruiterProfile.class));

        assertEquals("redirect:/dashboard", viewName);
    }

    @Test
    void testRegisterUserPageCandidateSuccess() {
        when(usersService.findByEmail(candidateNewUser.getEmail())).thenReturn(Optional.empty());

        String viewName = usersController.registerUserPage(model, candidateNewUser);

        verify(usersService, times(1)).save(candidateNewUser);
        verify(candidateProfileService, times(1)).save(any(CandidateProfile.class));

        assertEquals("redirect:/dashboard", viewName);
    }

    @Test
    void testRegisterUserPageEmailExist() {
        Users registeredUser = new Users();
        registeredUser.setEmail("already.registered@email.com");

        when(usersTypeService.getAll()).thenReturn(usersTypes);
        when(usersService.findByEmail(registeredUser.getEmail())).thenReturn(Optional.of(registeredUser));

        String viewName = usersController.registerUserPage(model, registeredUser);

        verify(model).addAttribute("error",
                "The email you have provided is already associated with an account.");
        verify(model).addAttribute("user", registeredUser);
        verify(model).addAttribute("types", usersTypes);

        assertEquals("register", viewName);
    }

}
