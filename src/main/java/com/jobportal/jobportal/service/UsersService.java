package com.jobportal.jobportal.service;

import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Optional<Users> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Users save(Users user) {
        return usersRepository.save(user);
    }
}
