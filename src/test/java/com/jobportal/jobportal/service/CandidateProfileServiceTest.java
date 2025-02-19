package com.jobportal.jobportal.service;

import com.jobportal.jobportal.entity.CandidateProfile;
import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.entity.UsersType;
import com.jobportal.jobportal.repository.CandidateProfileRepository;
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
public class CandidateProfileServiceTest {

    @Mock
    private CandidateProfileRepository candidateProfileRepository;

    @InjectMocks
    private CandidateProfileService candidateProfileService;

    CandidateProfile testCandidateProfile;

    @BeforeEach
    void setUp() {
        UsersType testType = new UsersType();
        testType.setId(1);
        testType.setType("RECRUITER");

        Users testUser = new Users();
        testUser.setId(1);
        testUser.setEmail("john.doe@email.com");
        testUser.setUsersType(testType);

        testCandidateProfile = new CandidateProfile();
        testCandidateProfile.setId(1);
        testCandidateProfile.setFirstName("John");
        testCandidateProfile.setLastName("Doe");
        testCandidateProfile.setCity("New York");
        testCandidateProfile.setCountry("USA");
        testCandidateProfile.setProfilePhoto("photo.jpg");
        testCandidateProfile.setResume("resume.pdf");
        testCandidateProfile.setUser(testUser);
    }

    @Test
    void testSaveCandidateProfile() {
        when(candidateProfileRepository.save(any(CandidateProfile.class))).thenReturn(testCandidateProfile);

        CandidateProfile savedProfile = candidateProfileService.save(testCandidateProfile);

        assertNotNull(savedProfile);
        assertEquals("John Doe", savedProfile.getFirstName()+" "+savedProfile.getLastName());

        verify(candidateProfileRepository, times(1)).save(testCandidateProfile);
    }

    @Test
    void testFindCandidateProfileById() {
        when(candidateProfileRepository.findById(1)).thenReturn(Optional.of(testCandidateProfile));

        Optional<CandidateProfile> foundProfile = candidateProfileService.findById(1);

        assertTrue(foundProfile.isPresent());
        assertEquals("John Doe", foundProfile.get().getFirstName()+" "+foundProfile.get().getLastName());

        verify(candidateProfileRepository, times(1)).findById(1);
    }

    @Test
    void testFindCandidateProfileByIdNotFound() {
        when(candidateProfileRepository.findById(0)).thenReturn(Optional.empty());

        Optional<CandidateProfile> foundProfile = candidateProfileService.findById(0);

        assertFalse(foundProfile.isPresent());

        verify(candidateProfileRepository, times(1)).findById(0);
    }
}
