package com.jobportal.jobportal.repository;

import com.jobportal.jobportal.entity.UsersType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersTypeRepository extends JpaRepository<UsersType, Integer> {
}
