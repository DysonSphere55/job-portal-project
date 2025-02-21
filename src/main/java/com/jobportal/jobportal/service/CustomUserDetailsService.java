package com.jobportal.jobportal.service;

import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.repository.UsersRepository;
import com.jobportal.jobportal.util.CustomUserDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found by email: " + username));
        return new CustomUserDetails(user);
    }
}
