package com.jobportal.jobportal.service;

import com.jobportal.jobportal.entity.UsersType;
import com.jobportal.jobportal.repository.UsersTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UsersTypeServiceTest {

    @Autowired
    private UsersTypeService usersTypeService;

    @Autowired
    private UsersTypeRepository usersTypeRepository;

    @BeforeEach
    void setUp() {
        usersTypeRepository.save(new UsersType(null, "RECRUITER"));
        usersTypeRepository.save(new UsersType(null, "CANDIDATE"));
    }

    @Test
    void testRequiredUsersTypesExist() {
        List<UsersType> usersTypes = usersTypeService.getAll();
        assertEquals(2, usersTypes.size(), "There should be exactly 2 types");
        usersTypes.forEach(System.out::println);
        assertTrue(usersTypes.stream().anyMatch(type -> type.getType().equals("RECRUITER")));
        assertTrue(usersTypes.stream().anyMatch(type -> type.getType().equals("CANDIDATE")));
    }
}
