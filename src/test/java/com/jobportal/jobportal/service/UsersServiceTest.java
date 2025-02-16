package com.jobportal.jobportal.service;

import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.entity.UsersType;
import com.jobportal.jobportal.repository.UsersRepository;
import com.jobportal.jobportal.repository.UsersTypeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UsersServiceTest {

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UsersTypeRepository usersTypeRepository;

    private Users testUser;

    @BeforeEach
    void setUp() {
        UsersType recruiterType = usersTypeRepository.save(new UsersType(null, "RECRUITER"));
        testUser = new Users(
                null, "test@example.com", "test123", true, new Date(), recruiterType
        );
        usersService.save(testUser);
    }
    @AfterEach
    void set() {
        usersRepository.deleteAll();
    }

    @Test
    void testUserSavedSuccessfully() {
        Optional<Users> dbUser = usersService.findByEmail("test@example.com");
        assertTrue(dbUser.isPresent(), "User should be found in the database");
        assertEquals("test@example.com", dbUser.get().getEmail());
    }

    @Test
    void testUserNotFound() {
        Optional<Users> dbUser = usersService.findByEmail("non-existent@example.com");
        assertFalse(dbUser.isPresent(), "User should be found in the database");
    }
}
