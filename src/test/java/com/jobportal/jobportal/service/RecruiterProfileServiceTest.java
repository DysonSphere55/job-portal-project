package com.jobportal.jobportal.service;

import com.jobportal.jobportal.entity.RecruiterProfile;
import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.entity.UsersType;
import com.jobportal.jobportal.repository.RecruiterProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecruiterProfileServiceTest {

    @Mock
    private RecruiterProfileRepository recruiterProfileRepository;
    @InjectMocks
    private RecruiterProfileService recruiterProfileService;
    RecruiterProfile testRecruiterProfile;

    @BeforeEach
    void setUp() {
        UsersType testType = new UsersType();
        testType.setId(1);
        testType.setType("RECRUITER");

        Users testUser = new Users();
        testUser.setId(1);
        testUser.setEmail("john.doe@email.com");
        testUser.setUsersType(testType);

        testRecruiterProfile = new RecruiterProfile();
        testRecruiterProfile.setId(1);
        testRecruiterProfile.setCompany("Tech Corp");
        testRecruiterProfile.setFirstName("John");
        testRecruiterProfile.setLastName("Doe");
        testRecruiterProfile.setCity("New York");
        testRecruiterProfile.setCountry("USA");
        testRecruiterProfile.setProfilePhoto("photo.jpg");
        testRecruiterProfile.setUser(testUser);
    }


    @Test
    void testSaveRecruiterProfile() {
        when(recruiterProfileRepository.save(any(RecruiterProfile.class))).thenReturn(testRecruiterProfile);

        RecruiterProfile savedProfile = recruiterProfileService.save(testRecruiterProfile);

        assertNotNull(savedProfile);
        assertEquals("Tech Corp", savedProfile.getCompany());

        verify(recruiterProfileRepository, times(1)).save(testRecruiterProfile);
    }

    @Test
    void testFindRecruiterProfileById() {
        when(recruiterProfileRepository.findById(1)).thenReturn(Optional.of(testRecruiterProfile));

        Optional<RecruiterProfile> foundProfile = recruiterProfileService.findById(1);

        assertTrue(foundProfile.isPresent());
        assertEquals("John Doe", foundProfile.get().getFirstName()+" "+foundProfile.get().getLastName());

        verify(recruiterProfileRepository, times(1)).findById(1);
    }

    @Test
    void testFindRecruiterProfileByIdNotFound() {
        when(recruiterProfileRepository.findById(0)).thenReturn(Optional.empty());

        Optional<RecruiterProfile> foundProfile = recruiterProfileService.findById(0);

        assertFalse(foundProfile.isPresent());

        verify(recruiterProfileRepository, times(1)).findById(0);
    }


}
