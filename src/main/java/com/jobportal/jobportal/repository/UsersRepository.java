package com.jobportal.jobportal.repository;

import com.jobportal.jobportal.entity.Users;
import com.jobportal.jobportal.entity.UsersType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByEmail(String email);
}
