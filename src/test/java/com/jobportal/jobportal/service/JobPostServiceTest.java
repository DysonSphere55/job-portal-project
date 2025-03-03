package com.jobportal.jobportal.service;

import com.jobportal.jobportal.dto.IRecruiterJobPost;
import com.jobportal.jobportal.dto.RecruiterJobPostDTO;
import com.jobportal.jobportal.entity.JobPost;
import com.jobportal.jobportal.repository.JobPostRepository;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobPostServiceTest {

    @Mock
    private JobPostRepository jobPostRepository;

    @InjectMocks
    private JobPostService jobPostService;


    @Test
    void testJobPostSave() {
        JobPost testJobPost = new JobPost();
        when(jobPostRepository.save(testJobPost)).thenReturn(testJobPost);

        JobPost savedJobPost = jobPostService.save(testJobPost);

        verify(jobPostRepository, times(1)).save(testJobPost);

        assertEquals(testJobPost, savedJobPost);
    }

    @Test
    void testGetRecruiterJobPosts() {

        int recruiterId = 1;

        IRecruiterJobPost iRecruiterJobPost = mock(IRecruiterJobPost.class);
        when(iRecruiterJobPost.getId()).thenReturn(1);
        when((iRecruiterJobPost.getTitle())).thenReturn("Test Job Post");
        when(iRecruiterJobPost.getCandidatesApplied()).thenReturn(5);
        when(iRecruiterJobPost.getJobLocationId()).thenReturn(11);
        when(iRecruiterJobPost.getCity()).thenReturn("New York");
        when(iRecruiterJobPost.getCountry()).thenReturn("USA");
        when(iRecruiterJobPost.getJobCompanyId()).thenReturn(111);
        when(iRecruiterJobPost.getName()).thenReturn("Test Tech Inc.");

        when(jobPostRepository.getRecruiterJobPosts(1)).thenReturn(List.of(iRecruiterJobPost));

        List<RecruiterJobPostDTO> result = jobPostService.getRecruiterJobPosts(1);

        verify(jobPostRepository, times(1)).getRecruiterJobPosts(1);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Test Job Post", result.get(0).getTitle());
        assertEquals(5, result.get(0).getCandidatesApplied());
        assertEquals(11, result.get(0).getJobLocation().getId());
        assertEquals("Test Tech Inc.", result.get(0).getJobCompany().getName());
    }
}
