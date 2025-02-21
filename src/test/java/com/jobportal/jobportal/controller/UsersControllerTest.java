package com.jobportal.jobportal.controller;


import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.entity.UsersType;
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
    private Model model;

    @InjectMocks
    private UsersController usersController;

    private List<UsersType> usersTypes;

    @BeforeEach
    void setUp() {
        UsersType recruiterType = new UsersType(1, "RECRUITER");
        UsersType candidateType = new UsersType(2, "CANDIDATE");
        usersTypes = List.of(recruiterType, candidateType);
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
    void testRegisterUserPageSuccess() {
        Users user = new Users();
        user.setEmail("john.doe@email.com");

        when(usersService.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        String viewName = usersController.registerUserPage(model, user);

        verify(usersService, times(1)).save(user);

        assertEquals("dashboard", viewName);
    }

    @Test
    void testRegisterUserPageEmailExist() {
        Users user = new Users();
        user.setEmail("existing@email.com");

        when(usersTypeService.getAll()).thenReturn(usersTypes);
        when(usersService.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        String viewName = usersController.registerUserPage(model, user);

        verify(model).addAttribute("error",
                "The email you have provided is already associated with an account.");
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("types", usersTypes);

        assertEquals("register", viewName);
    }

}
